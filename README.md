<p align="center">
  <img src="https://www.corda.net/wp-content/uploads/2016/11/fg005_corda_b.png" alt="Corda" width="500">
</p>

# Corda Service auto-trigger 

## Running the nodes

Run the gradle tasks to run the nodes. 

    task deployNodes(type: net.corda.plugins.Cordform, dependsOn: ['jar']) {

## Interacting with the nodes

### Shell

When started via the command line, each node will display an interactive shell:

    Welcome to the Corda interactive shell.
    Useful commands include 'help' to see what is available, and 'bye' to shut down the node.
    
    Tue Nov 06 11:58:13 GMT 2018>>>

You can use this shell to interact with your node. For example, enter `run networkMapSnapshot` to see a list of 
the other nodes on the network:

    Thu Nov 07 13:47:26 IST 2019>>> run networkMapSnapshot
        - addresses:
            - "localhost:10014"
            legalIdentitiesAndCerts:
            - "O=BankOperator, L=Toronto, C=CA"
             platformVersion: 4
            serial: 1573114339175
       - addresses:
            - "localhost:10011"
             legalIdentitiesAndCerts:
            - "O=Employee2, L=San Diego, C=US"
            platformVersion: 4
            serial: 1573114338564
      - addresses:
           - "localhost:10002"
            legalIdentitiesAndCerts:
            - "O=Notary, L=London, C=GB"
             platformVersion: 4
             serial: 1573114333232
      - addresses:
         - "localhost:10008"
            legalIdentitiesAndCerts:
         - "O=Employee1, L=New York, C=US"
            platformVersion: 4
         serial: 1573114325279
     - addresses:
         - "localhost:10005"
           legalIdentitiesAndCerts:
         - "O=Yudiz, L=London, C=GB"
        platformVersion: 4
        serial: 1573114385728


### Working structure 
    Yudiz node will offer finance service to Employee1 of Yudiz. Keeping one thing that Bank node will get only transaction state
    The CordaService will create new state for Employee1 via Bank without any single transaction sign by Bank node. 
    
   ##### Step 1 Check this command in all Nodes
   
    run internalVerifiedTransactionsSnapshot
 
   ###### BankOfCorda 
   
    run internalVerifiedTransactionsSnapshot
    []
    
   ###### Employee1
   
    run internalVerifiedTransactionsSnapshot
    []
    
   ###### Employee2
   
    run internalVerifiedTransactionsSnapshot
    []
    
   ###### Yudiz 
   
    run internalVerifiedTransactionsSnapshot
    [] 
    
  
  ##### Step 2 Creates the transaction in Yudiz node 
  
    flow start RequestFlowInitiator amount: 500, towhom: Employee1
    
  ##### Step 3 fastly check the nodes of Bank and Employee1 by run internalVerifiedTransactionsSnapshot
  
    Bravo magic Bank Corda has also got notification and the amount/State is tranfer from Yudiz to Bank to Employee1
    without any notice of Bank or any click event by Bank. 
   
