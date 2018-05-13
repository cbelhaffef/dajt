package com.cbelhaffef.dajt.dataset;

import com.cbelhaffef.dajt.model.court.Court;
import com.cbelhaffef.dajt.model.folder.Folder;
import com.cbelhaffef.dajt.model.guilty.Guilty;
import com.cbelhaffef.dajt.model.victim.Victim;
import com.google.common.collect.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FoldersDS {

    Folder folder1;
    Folder folder2;
    Folder folder3;
    Folder folder4;

    public FoldersDS() {
        folder1 = new Folder();
        folder1.setNumber("n1");
        folder1.setDirectionNumber("dn1");
        folder1.setVictims(Sets.newHashSet(Arrays.asList(new Victim("v1"))));
        folder1.setGuilties(Sets.newHashSet(Arrays.asList(new Guilty("g1"), new Guilty("g12"))));
        folder1.setOffence("o1");
        folder1.setCourt(new Court("c1"));
        folder1.setSendingType("d1");

        folder2 = new Folder();
        folder2.setNumber("n2");
        folder2.setDirectionNumber("dn2");
        folder2.setVictims(Sets.newHashSet(Arrays.asList(new Victim("v2"),new Victim("v22"))));
        folder2.setGuilties(Sets.newHashSet(Arrays.asList(new Guilty("g2"))));
        folder2.setOffence("o2");
        folder2.setCourt(new Court("c2"));
        folder2.setSendingType("d2");

        folder3 = new Folder();
        folder3.setNumber("n3");
        folder3.setDirectionNumber("dn3");
        folder3.setVictims(Sets.newHashSet(Arrays.asList(new Victim("v3"))));
        folder3.setGuilties(Sets.newHashSet(Arrays.asList(new Guilty("g3"))));
        folder3.setOffence("o3");
        folder3.setCourt(new Court("c3"));
        folder3.setSendingType("d3");

        folder4 = new Folder();
        folder4.setNumber("n4");
        folder4.setDirectionNumber("dn4");
        folder4.setVictims(Sets.newHashSet(Arrays.asList(new Victim("v4"))));
        folder4.setGuilties(Sets.newHashSet(Arrays.asList(new Guilty("g4"))));
        folder4.setOffence("o4");
        folder4.setCourt(new Court("c4"));
        folder4.setSendingType("d4");
    }

    public Folder getFolder1() {
        return folder1;
    }

    public Folder getFolder2() {
        return folder2;
    }

    public Folder getFolder3() {
        return folder3;
    }

    public Folder getFolder4() {
        return folder4;
    }

    public List<Folder> getAll(){
        return Arrays.asList(getFolder1(),getFolder2(),getFolder3(),getFolder4());
    }
}
