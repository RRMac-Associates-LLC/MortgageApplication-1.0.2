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

println("** copy file.txt to file-original.txt")
def src = new File("$CODE_BASE_DIR/MortgageApplication/build/files.txt")
def dst = new File("$CODE_BASE_DIR/MortgageApplication/build/files-original1.txt")
dst << src.text

//def outFile = new File("$CODE_BASE_DIR/MortgageApplication/build/fils.txt")

//commitInfo=$($gitScript ls-remote $gitURL $gitBranch)
def proc = "$gitScript ls-remote $gitURL $gitBranch".execute()
def commitID=proc.text.split()
println "commitID="+commitID[0]


def proc2 = "$gitScript diff --name-only $commitID[0] HEAD~1".execute()
def b2 = new StringBuffer()
proc2.consumeProcessErrorStream(b2)
def gitChanges=proc2.text
println "proc2.text="+proc2.text





















