// Typings reference file, you can add your own global typings here
// https: //www.typescriptlang.org/docs/handbook/writing-declaration-files.html
interface WebpackRequire {
    <T>(path: string): T;
    (paths: string[], callback: (...modules: any[]) => void): void;
    ensure: (paths: string[], callback: (require: <T>(path: string) => T) => void) => void;
}
interface NodeRequire extends WebpackRequire {}

declare var System:  any;
declare var require:  NodeRequire;
