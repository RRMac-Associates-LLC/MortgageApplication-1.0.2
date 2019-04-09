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

def now = new Date()
def timestamp = now.format("yyyyMMdd-HHmmss.SSS", TimeZone.getTimeZone('UTC'))
println("** copy file.txt to file-" + timestamp + ".txt")
def src = new File("$CODE_BASE_DIR/MortgageApplication/build/files.txt")
src.renameTo "$CODE_BASE_DIR/MortgageApplication/build/files-" + timestamp + ".txt"
//def dst = new File("$CODE_BASE_DIR/MortgageApplication/build/files-original1.txt")
//dst << src.text

//def outFile = new File("$CODE_BASE_DIR/MortgageApplication/build/files.txt")

//commitInfo=$($gitScript ls-remote $gitURL $gitBranch)
def proc = "$gitScript ls-remote $gitURL $gitBranch".execute()
def commitIDs=proc.text.split()
def commitID=commitIDs[0]
println "commitID="+commitID

println "$gitScript diff --name-only $commitID HEAD~1"
def proc2 = "$gitScript diff --name-only $commitID HEAD~1".execute()
def gitChanges=proc2.text.split()

println "gitChanges="+gitChanges
File outFile = new File("$CODE_BASE_DIR/MortgageApplication/build/files.txt")

//outFile.withWriter{ out -> 
//  gitChanges.each {out.println it.name}
//}

for(obj in gitChanges) 
{
    println "obj="+obj
    outFile << obj
}




















