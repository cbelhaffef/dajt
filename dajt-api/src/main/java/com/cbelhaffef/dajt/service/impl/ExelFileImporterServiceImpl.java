package com.cbelhaffef.dajt.service.impl;

import com.cbelhaffef.dajt.api.accused.Accused;
import com.cbelhaffef.dajt.api.court.Court;
import com.cbelhaffef.dajt.api.court.CourtRepo;
import com.cbelhaffef.dajt.api.folder.Folder;
import com.cbelhaffef.dajt.api.folder.FolderRepo;
import com.cbelhaffef.dajt.api.imports.Import;
import com.cbelhaffef.dajt.api.imports.ImportRepo;
import com.cbelhaffef.dajt.api.office.Office;
import com.cbelhaffef.dajt.api.office.OfficeRepo;
import com.cbelhaffef.dajt.api.priority.Priority;
import com.cbelhaffef.dajt.api.priority.PriorityRepo;
import com.cbelhaffef.dajt.api.status.Status;
import com.cbelhaffef.dajt.api.status.StatusRepo;
import com.cbelhaffef.dajt.api.vicitm.Victim;
import com.cbelhaffef.dajt.api.folder.FolderColumnImportEnum;
import com.cbelhaffef.dajt.api.folder.FolderPriorityEnum;
import com.cbelhaffef.dajt.api.status.StatusFolder;
import com.cbelhaffef.dajt.api.imports.StatusImport;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cbelhaffef.dajt.service.ExelFileImporterService;

import javax.management.BadAttributeValueExpException;
import java.io.*;
import java.nio.file.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cbelhaffef.dajt.api.folder.FolderTopicEnum.UNKOWN;
import static com.cbelhaffef.dajt.api.imports.StatusImport.IN_PROGRESS;
import static com.cbelhaffef.dajt.api.imports.StatusImport.ON_ERROR;


@Service
@Slf4j
public class ExelFileImporterServiceImpl implements ExelFileImporterService {

    @Autowired
    private FolderRepo folderRepo;

    @Autowired
    private OfficeRepo officeRepo;

    @Autowired
    private CourtRepo courtRepo;

    @Autowired
    private ImportRepo importRepo;

    @Autowired
    private StatusRepo statusRepo;

    @Autowired
    private PriorityRepo priorityRepo;

    private final static String ARCHIVE_DIRECTORY = "/mnt/imports/archives";

    private final static String TAG_DATE_OF_OFFENCE = "بتاريـخ";
    private final static String TAG_UNKOWN = "مجهول";
    private final static String TAG_BIS_AR = "مكرر";
    private final static String TAG_BIS_FR = "BIS";

    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private SimpleDateFormat formatterArch = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Set<Folder> doImport(File fileExel) {
        return doImport(fileExel, false);
    }

    @Override
    @Transactional
    public Set<Folder> doImport(File fileExel , boolean reversedKey) {

        Optional<Status> statusOpen = statusRepo.findByCode(StatusFolder.OPEN.name());

        Optional<Priority> priortyMinor = priorityRepo.findByCode(FolderPriorityEnum.MINOR.name());

        Set<Folder> folders = new TreeSet<>();

        Import imp = new Import();
        imp.setFileName(fileExel.getAbsolutePath());
        try{
            if(!fileExel.exists()) {
                String msg = "Le fichier n'existe pas";
                throw new FileNotFoundException(msg);
            }

            List<Import> imports= importRepo.findByFileNameAndStatus(fileExel.getAbsolutePath(), StatusImport.FINICHED);

            if(!imports.isEmpty()){
                String msg = "le fichier a déja été importé dans le system.";
                throw new FileAlreadyExistsException(msg);
            }

            // start imports
            imp.setStatus(IN_PROGRESS);
            Path filePath = fileExel.toPath();
            Path filePathTemp = null;
            imp.setFileSize(Files.size(filePath));
            imp = importRepo.save(imp);


            // fileName
            String fileName = fileExel.getName();
            String[] args = fileName.split("_");
            if(args.length < 3 ){
                String msg = "Nom du fichier ne respecte pas le pattern : type_numDossier_annee_numbFile";
                throw new BadAttributeValueExpException(msg);
            }
            // officeNumber
            String officeNumber = args[1];
            if(officeNumber.length() > 2){
                String msg = "le nom du bureau n'est pas conforme";
                throw new BadAttributeValueExpException(msg);
            }

            String yearOfFolders = args[2];

            Long OfficeId = new Long(officeNumber.substring(1));
            Office office = officeRepo.getOne(OfficeId);

            log.info("============= Debut de l'importation pour le bureau '" + office.getName()+ "' du fichier : " + fileExel.getAbsolutePath());

            InputStream streamExel = new ByteArrayInputStream(convertFileIntoByteArray(fileExel));

            //change file to temp
            filePathTemp = renameFileWhen(filePath.toFile(),IN_PROGRESS).toPath();

            fileExel = filePathTemp.toFile();

            Workbook workbook = new XSSFWorkbook(streamExel);
            Sheet datatypeSheet = workbook.getSheetAt(0); // choose year on sheet
            Iterator<Row> iterator = datatypeSheet.iterator();

            //Skip the header (first row)
            int count = 1;
            if (iterator.hasNext()) iterator.next();
            Folder previousFolder = null;
            while (iterator.hasNext()) {
                count++;
                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                Folder folder = new Folder();
                Date date = formatter.parse("01/01/"+yearOfFolders);
                folder.setCreated(date);
                folder.setUpdated(date);
                folder.setStatus(statusOpen.isPresent() ? statusOpen.get() : null);
                folder.setPriority(priortyMinor.isPresent() ? priortyMinor.get() : null);

                folder.setOffice(office);

                log.debug("processing row number : " + count);
                boolean isErrorProcessing = false;
                // treatment of the boxes of the current line
                for(int i = 0 ; cellIterator.hasNext() && !isErrorProcessing ; i++){
                    Cell currentCell = cellIterator.next();

                    // key
                    if (i == FolderColumnImportEnum.KEY.getValue()) {
                        if (currentCell.getCellTypeEnum() == CellType.BLANK) {
                            log.warn("la ligne " + count + " du fichier d'imports " + fileExel.getName() + " à été ignorée car le numéro du dossier est absent");
                            isErrorProcessing = true;
                            break;
                        } else if (currentCell.getCellTypeEnum() == CellType.STRING && !currentCell.getStringCellValue().isEmpty()) {

                            // format Key
                            // first tag BIS
                            String key = currentCell.getStringCellValue().replace(TAG_BIS_AR, TAG_BIS_FR);
                            // second \n to " "
                            key = key.replace("\n", " ");
                            // reverse if it given
                            key = reverseKey(key, reversedKey);
                            Optional<Folder> folderDb = folderRepo.findByNumber(key);
                            if (folderDb.isPresent()) {
                                log.info("Le dossier numéro : " + key + " à déja été importé.");
                                isErrorProcessing = true;
                                break;
                            }
                            log.debug("processing folder number : " + key);
                            folder.setNumber(key);
                        }
                    }
                    // direcotrateKey
                    if (i == FolderColumnImportEnum.DIRECTORATE_KEY.getValue() &&
                        currentCell.getCellTypeEnum() == CellType.STRING &&
                        !currentCell.getStringCellValue().isEmpty()){
                        folder.setDirectorateNumber(currentCell.getStringCellValue());
                    }

                    // Victims
                    if (i == FolderColumnImportEnum.VICTIMS.getValue() &&
                        currentCell.getCellTypeEnum() == CellType.STRING &&
                        !currentCell.getStringCellValue().isEmpty()) {
                        String stringVictims = currentCell.getStringCellValue();
                        Set<Victim> victims = new HashSet<>();
                        if(stringVictims.contains("\n")){
                            String[] arr = stringVictims.split("\n");
                            for(String a : arr){
                                if(!a.isEmpty()){
                                    if(a.matches("(\\?){2,}")){
                                        a = TAG_UNKOWN;
                                    }
                                    Victim v = new Victim(a, folder);
                                    victims.add(v);
                                }
                            }
                        }else{
                            victims = Sets.newHashSet(new Victim(stringVictims,folder));
                        }
                        folder.setVictims(victims);
                    }

                    // Accused
                    if (i == FolderColumnImportEnum.ACCUSED.getValue() &&
                        currentCell.getCellTypeEnum() == CellType.STRING &&
                        !currentCell.getStringCellValue().isEmpty()) {
                        String stringGuilties = currentCell.getStringCellValue();
                        Set<Accused> accused = new HashSet<>();
                        if(stringGuilties.contains("\n")){
                            String[] arr = stringGuilties.split("\n");
                            for(String a : arr){
                                if(!a.isEmpty()){
                                    if(a.matches("(\\?){2,}")){
                                        a = TAG_UNKOWN;
                                    }
                                    Accused ac = new Accused(a, folder);
                                    accused.add(ac);
                                }
                            }
                        }else{
                            accused = Sets.newHashSet(new Accused(stringGuilties,folder));
                        }
                        folder.setAccused(accused);
                    }

                    // Offence
                    if (i == FolderColumnImportEnum.OFFENCE.getValue()) {
                        String offenceValue = null;
                        if(currentCell.getCellTypeEnum() == CellType.BLANK){
                            offenceValue = UNKOWN.getValue();
                        }else if (currentCell.getCellTypeEnum() == CellType.STRING && !currentCell.getStringCellValue().isEmpty()){
                            offenceValue = currentCell.getStringCellValue();
                            if(offenceValue.matches("(/){2,}") && i > 0 && previousFolder.getOffence() != null && !previousFolder.getOffence().isEmpty()){
                                offenceValue = previousFolder.getOffence();
                                folder.setOffenceDate(extractDateFromString(offenceValue));
                            }
                        }
                        folder.setOffence(offenceValue);
                    }

                    // Court
                    if (i == FolderColumnImportEnum.COURT.getValue()) {
                        String courtName = null;
                        Court court = null;
                        if(currentCell.getCellTypeEnum() == CellType.BLANK) {
                            courtName = UNKOWN.getValue();
                        } else if (currentCell.getCellTypeEnum() == CellType.STRING && !currentCell.getStringCellValue().isEmpty()) {
                            courtName = currentCell.getStringCellValue();
                            if(courtName.matches("(/){2,}") && i > 0 && previousFolder.getCourt() != null){
                                court = previousFolder.getCourt();
                            }
                        }
                        if (court != null) {
                            Optional<Court> courtDb = courtRepo.findByName(courtName);
                            if(!courtDb.isPresent()){
                                court = new Court(courtName);
                            }else{
                                court = courtDb.get();
                            }
                        }
                        folder.setCourt(court);
                    }

                    // ADMINISTRATION_CONCERNED
                    if (i == FolderColumnImportEnum.ADMINSTRATION_CONCERNED.getValue() &&
                        currentCell.getCellTypeEnum() == CellType.STRING &&
                        !currentCell.getStringCellValue().isEmpty() && !currentCell.getStringCellValue().equals("//")) {
                        folder.setAdministrationConcerned(currentCell.getStringCellValue());
                    }

                }

                if(!isErrorProcessing){
                    // save folder
                    folder = folderRepo.save(folder);
                    previousFolder = folder;
                    folders.add(folder);
                }
            }

            imp.setEnded(new Date());
            imp.setMessage("le fichier " + filePath.getFileName() + " a été importé avec succées");
            imp.setStatus(StatusImport.FINICHED);
            imp = importRepo.save(imp);
            File dirArchive = new File(ARCHIVE_DIRECTORY);
            if(!dirArchive.exists()){
                dirArchive.mkdirs() ;
            }
            String toDayString = formatterArch.format(new Date());

            File direArchiveToDay = new File(ARCHIVE_DIRECTORY + File.separator + toDayString);
            if(!direArchiveToDay.exists()){
                direArchiveToDay.mkdirs() ;
            }
            // Move imports file into archive directory when finished
            Path target = Paths.get(direArchiveToDay.getAbsolutePath());
            Files.move(filePathTemp,target.resolve(filePath.getFileName()));

            log.info("============= Fin de l'importation pour le bureau '" + office.getName()+ "' du fichier : " + imp.getFileName());

        } catch ( Exception e ) {
            endImportOnError(fileExel,imp,e.toString() + " - " + e.getMessage());
            e.printStackTrace();
        }

        return folders;
    }

    /**
     * Reverse key
     * @param key
     * @param reversedKey
     * @return
     */
    public String reverseKey(String key, boolean reversedKey){
        if(reversedKey){
            String n1,n2,n3 = null;
            StringBuilder keyBuilder = new StringBuilder();
            String[] arrayKey = key.split("/");
            n2 = arrayKey[0];
            if(arrayKey.length > 1){
                String[] yearOrNumber = arrayKey[1].split(" ");
                n1 = yearOrNumber[0];
                keyBuilder.append(n1);
                if(yearOrNumber.length > 2){
                    n3 = yearOrNumber[1];
                }
                keyBuilder.append("/");
                keyBuilder.append(n2);
                if(n3 != null){
                    keyBuilder.append(" ");
                    keyBuilder.append(n3);
                }

            }else{
                throw new IllegalArgumentException("Le numéro du dossier " +  key + "est mal formé");
            }
            return keyBuilder.toString();
        }
        return key;
    }

    public File renameFileWhen(File fileToRename, StatusImport status) throws IOException {
        if(fileToRename == null || !fileToRename.exists()){
            throw new FileNotFoundException("le fichier n'exite pas");
        }
        String fileName = fileToRename.getName();
        if(status.equals(ON_ERROR) && fileName.endsWith(IN_PROGRESS.name())){
            fileName = fileName.substring(0,fileName.indexOf(IN_PROGRESS.name()));
        }
        File fileRenamed = new File(fileToRename.getParent(), fileName + status.name());
        Files.copy(fileToRename.toPath(),fileRenamed.toPath(), StandardCopyOption.REPLACE_EXISTING);
        fileToRename.delete();
        return fileRenamed;
    };

    public byte[] convertFileIntoByteArray(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[fis.available()];
        fis.read(data);
        fis.close();
        return data;
    }

    public void endImportOnError(File file, Import imp, String message) {
        String msg = "L'imports du fichier : " + imp.getFileName() +" a échoué - " + message;
        log.error(msg);
        imp.setStatus(ON_ERROR);
        imp.setEnded(new Date());
        imp.setMessage(msg);
        importRepo.save(imp);

        try {
            if(file.exists()) {
                file = renameFileWhen(file, ON_ERROR);
            }
        } catch (IOException e) {
            log.error("Le fichier : " + file.getName() + " n'existe pas.");
            e.printStackTrace();
        }
    }

    public Date extractDateFromString(String text) throws ParseException {

        String regex = "\\d{2}/\\d{2}/\\d{4}";
        Matcher m = Pattern.compile(regex).matcher(text);
        if (m.find()) {
            return formatter.parse(m.group(0));
        }
        return null;
    }
}
