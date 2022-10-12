def restartNode(node_label){

    node ("$node_label") {
        print "Rebooting $node_label........."
        if (isUnix()) {
            sh "sudo shutdown -r +1"
        } else {
            bat "shutdown /r /f"
        }
    }

    print "Waiting for the $node_label to reboot........."
    sleep(time:30,unit:"SECONDS")

    def numberOfAttemps = 0
    while (numberOfAttemps < 20) {
        if(Jenkins.instance.getNode(node_label).toComputer().isOnline()){
            print "Node $node_label is rebooted and ready to use"
            return true;
        }
        print "Waiting for the $node_label to reboot........."
        sleep(time:20,unit:"SECONDS")
        numberOfAttemps += 1
    }
    print "Node $node_label is not up running"
    return false
}

def getNodeName(){

    if (run.contains("ov") || run_specific_perf_test.contains("ov")){
        echo "open vino workload is selected ..."
        return 'graphene_icl_sdp_03'
    } else if (run.contains("redis") || run_specific_perf_test.contains("redis")){
        echo "redis workload is selected ..."
        return 'graphene_icl_sdp_03'
    } else if (run.contains("tf") || run_specific_perf_test.contains("tf")) {
        echo "tensorflow workload is selected ..."
        return 'graphene_icl_sdp_03'
    }

}

return this