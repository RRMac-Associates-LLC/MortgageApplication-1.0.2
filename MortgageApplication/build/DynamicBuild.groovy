def CODE_BASE_DIR = "/u/jenkins/workspace/GIT_Project/testDBB1.0.2"
def gitScript="/usr/lpp/IBM/dbb/bin/git-jenkins.sh"
def gitURL="git@github.com:RRMac-Associates-LLC/MortgageApplication-1.0.2.git"
def gitBranch="master"

println("** in DynamicBuild.grooy **")


println("** in DynamicBuild.grooy **")
println("** CODE_BASE_DIR="+CODE_BASE_DIR)
println("** gitScript="+gitScript)
println("** gitURL="+gitURL)
println("** gitBranch="+gitBranch)

//backup files.txt that is coming in from git
def now = new Date()
def timestamp = now.format("yyyyMMdd-HHmmss.SSS", TimeZone.getTimeZone('UTC'))
//println("** copy file.txt to file-" + timestamp + ".txt")
println("** copy file.txt to file-original.txt")
def src = new File("$CODE_BASE_DIR/MortgageApplication/build/files.txt")
//src.renameTo "$CODE_BASE_DIR/MortgageApplication/build/files-" + timestamp + ".txt"
def dst = new File("$CODE_BASE_DIR/MortgageApplication/build/files-original.txt")
dst << src.text
//clean out files.txt
src.withWriter { writer ->
        writer.write("")
}

//def outFile = new File("$CODE_BASE_DIR/MortgageApplication/build/files.txt")

//get incoming commitID
//commitInfo=$($gitScript ls-remote $gitURL $gitBranch)
def proc = "$gitScript ls-remote $gitURL $gitBranch".execute()
def commitIDs=proc.text.split()
def commitID=commitIDs[0]
println "commitID="+commitID

//need to check incoming commitID to last commitID that was built...this is saved in a file.  
//if file does not exist, then get last commitID from git using HEAD~1
File lastCommitIDBuiltFile = new File(CODE_BASE_DIR+'/lastCommitIDBuilt.txt')
String previousCommitID = ""

//test from command line
// /usr/lpp/IBM/dbb/groovy-2.4.12/groovyz /u/jenkins/workspace/GIT_Project/testDBB1.0.2/MortgageApplication/build/DynamicBuild.groovy

//set prevoiusCommitID to the needed type
if(!lastCommitIDBuiltFile.exists() || lastCommitIDBuiltFile.text == '')
{
    previousCommitID = "HEAD~1"
}
else
{
    previousCommitID = lastCommitIDBuiltFile.text
}

println "previousCommitID="+previousCommitID

if(previousCommitID != commitID)
{    
    //$gitScript diff --name-only $commitID HEAD~1
    println "$gitScript diff --name-only $commitID $previousCommitID"
    def proc2 = "$gitScript diff --name-only $commitID $previousCommitID".execute()    
    def gitChanges=proc2.text.split()
    println "proc2.text="+gitChanges.length  

    println "gitChanges="+gitChanges[]
    File outFile = new File("$CODE_BASE_DIR/MortgageApplication/build/files.txt")

    //outFile.withWriter{ out -> 
    //  gitChanges.each {out.println it.name}
    //}

    for(obj in gitChanges) 
    {
        println "obj="+obj
        //outFile << obj
 
        outFile.withWriterAppend{ out ->
                out.println obj
        }
    }
    
    //update last commitID in file
    lastCommitIDBuiltFile.withWriter { writer ->
        writer.write(commitID)
    }
     
}
else
{
   println "!!!!!!! Commit already built - " + previousCommitID + " = " + commitID
}



















