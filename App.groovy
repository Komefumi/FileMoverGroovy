import java.io.File
import java.nio.file.Files
import java.nio.file.FileSystemException

class App {
  static void main(String... args) {
    println args
    // println args.class
    // println args.split(',').collect{it as String}
    def sourceDir = System.properties["user.dir"]
    def targetDir = System.properties["user.dir"]
    def sources = []
    def prefix = "";
    for(int i = 0; i < args.size() ; i++) {
      if (args[i].startsWith("--target=")) {
        def val = args[i][(args[i].indexOf('=') + 1)..-1];
        def seperatorIdx = val.indexOf('/');
        if ((seperatorIdx == -1)) {
          prefix=val
        } else {
          def neo_dir = val[0..seperatorIdx]
          File neoDir = new File(neo_dir)
          boolean it_exists = neoDir.exists()
          boolean is_dir = neoDir.isDirectory()
          if (it_exists && !is_dir) {
            println "The provided directory to put copies to, is not really a directory."
            println "Exiting."
            System.exit(1)
          } else if (!it_exists) {
            neoDir.mkdir()
          }
          targetDir = targetDir + "/" + neo_dir
          prefix = val[seperatorIdx..-1]
        }
      } else {
        sources.push(args[i])
      }
    }
    
    if (prefix == "") {
      println "Must specify target"
      System.exit(1);
    }

    for(int i = 0; i < sources.size() ; i++) {
      def it = sources[i]
      try {
        Files.copy(new File(sourceDir + "/" + it).toPath(), new File(targetDir + "/" + prefix + it).toPath())
      } catch (FileSystemException e) {
        def offendingFile = e.getFile()
        println "The file $offendingFile does not exist. Re-check and try again."
        for (int j = 0; j < i; j++) {
          if (j == 0) {
            println "Files copied upto this point:"
          }
          println sources[j]
        }
        println "Exiting..."
        System.exit(0)
      }
    }

    println "Operation complete, check the target folder: $targetDir"
    System.exit(0)
  }
}