// https://firebase.google.com/docs/functions/get-started

const functions = require(`firebase-functions`);
const admin = require(`firebase-admin`);
const { firestore } = require("firebase-admin");
admin.initializeApp();

/**
 * This function adds a document to firestore.
 * @param HTML request which contains data in json format.
 * @returns HTML response which indicates success or failure.
 */
exports.addToFirestore = functions.https.onRequest((request, response) => {
    console.log('Starting \`addToFirestore\` execution.');
    
    const collectionPath = request.query.collection;  // Collection path
    const docId = request.query.username;             // Document ID
    const data = request.body;                        // Data to be added to Firestore

    admin.firestore().collection(collectionPath).doc(docId).set(data)
        .then(doc => {
        console.log(`Data added to Firestore collection "${collectionPath}" with ID: ${doc.id}`);
        response.send(`Data added to Firestore collection "${collectionPath}" with ID: ${doc.id}`);
        })
        .catch(error => {
        console.error(`Error adding data to Firestore: `, error);
        response.status(500).send(error);
        });
});


/**
 * This function removes a document from firestore.
 * @param HTML request which contains collecition ID and document ID.
 * @returns HTML response which indicates success or failure.
 */
exports.removeFromFirestore = functions.https.onRequest((request, response) => {
    console.log('Starting \`removeFromFirestore\` execution.');
    
    const collectionPath = request.query.collection;  // Collection path
    const docId = request.query.username;             // ID of the document to be deleted
    
    admin.firestore().collection(collectionPath).doc(docId).delete()
        .then(() => {
            console.log(`Data removed from Firestore collection "${collectionPath}"`);
            response.send(`Data removed from Firestore collection "${collectionPath}"`);
        })
        .catch(error => {
            console.error(`Error removing data from Firestore: `, error);
            response.status(500).send(error);
        });
});


/**
 * This function retreives a document from firestore
 * @param HTML request which contains collection ID and document ID.
 * @returns HTML response which contains a document in json format.
 */
exports.getFromFirestore = functions.https.onRequest((request, response) => {
    console.log('Starting \`getFromFirestore\` execution.');

    const collectionPath = request.query.collection;  // Collection path
    const docId = request.query.document;             // Document ID

    admin.firestore().collection(collectionPath).doc(docId).get()
        .then(doc => {
        if (doc.exists) {
            console.log(`Document data retrieved from Firestore: `, doc.data());
            response.send({data : doc.data()});       // document data as JSON object
        }
        else {
            console.log(`Document not found in Firestore collection "${collectionPath}"`);
            response.status(404).send(`Document not found in Firestore collection "${collectionPath}"`);
        }
        })
        .catch(error => {
        console.error(`Error getting document from Firestore: `, error);
        response.status(500).send(error);
        });
});


/**
 * This function retrieves an array of documents from firestore.
 * This functions is used exclusively by 'appointments' collection.
 * @param HTML request which contains collection ID, user name, date and boolean that indicates whether a client or not.
 * @returns HTML response which contains an array of documents, each document in json format.
 */
exports.getAppointmentsFromFirestore = functions.https.onRequest((request, response) => {
    console.log('Starting \`getAppointmentsFromFirestore\` execution.');
    const collectionPath = request.query.collection;  // Collection path
    const userName = request.query.username;          // User Name received in query
    const date = request.query.date;                  // Date of appointment
    const isClient = request.query.isclient;          // Is the User client or business?

    // Query all documents in the collection
    const query = admin.firestore().collection(collectionPath);
    query.get()
        .then(snapshot => {
        let documents = [];                           // Array to store document data

        snapshot.forEach(doc => {
            var docIdArray = doc.id.split('-');
            
            if (isClient == "true") {
                if (docIdArray[1] != userName) {      // Skip appointments with other clients
                    return;
                }
            }
            else {
                if (docIdArray[0] != userName) {      // Skip appointments with other businesses
                    return;
                }
            }
            if (doc.data().date != date) {              // Skip appointments on other dates
                return;
            }
            
            console.log(doc.id);
            documents.push({doc : doc.data()});       // Add document data to array
        });

        console.log(`Documents retrieved from Firestore: `, documents);
        response.send(documents);                     // documents data as JSON array
        })
        .catch(error => {
        console.error(`Error getting documents from Firestore: `, error);
        response.status(500).send(error);
      });
  });


/**
 * This function retrieves an array of documents from firestore.
 * This functions is used exclusively by 'services' collection.
 * @param HTML request which contains collection ID and business user name.
 * @returns HTML response which contains an array of documents, each document in json format.
 */
exports.getServicesFromFirestore = functions.https.onRequest((request, response) => {
    console.log('Starting \`getServicesFromFirestore\` execution.');
    const collectionPath = request.query.collection;  // Collection path
    const userName = request.query.username;          // User Name received in query

    // Query all documents in the collection
    const query = admin.firestore().collection(collectionPath);
    query.get()
        .then(snapshot => {
        let documents = [];                           // Array to store document data

        snapshot.forEach(doc => {
            var docIdArray = doc.id.split('-');
            
            if (docIdArray[0] != userName) {
                return;
            }
            
            console.log(doc.id);
            documents.push({doc : doc.data()});       // Add document data to array
        });

        console.log(`Documents retrieved from Firestore: `, documents);
        response.send(documents);                     // documents data as JSON array
        })
        .catch(error => {
        console.error(`Error getting documents from Firestore: `, error);
        response.status(500).send(error);
        });
});


/**
 * This function retrieves an array of documents, each document from 'business' collection.
 * @param HTML request which contains collection ID and business name.
 * @returns HTML response which contains an array of business documents, each document in json format.
 */
exports.getBusinessByNameFromFirestore = functions.https.onRequest((request, response) => {
    console.log('Starting \`getBusinessByNameFromFirestore\` execution.');

    const collectionPath = request.query.collection;  // Collection path
    const businessName = request.query.businessname;  // Document ID

    console.log(`collection: ${collectionPath}`);
    console.log(`business_name: ${businessName}`);

    admin.firestore().collection(collectionPath)
                            .where('business_name', "==", businessName).get()
        .then(snapshot => {
            var documents = [];                       // Array to store document data
            
            snapshot.forEach(doc => {
                documents.push({data : doc.data()});  // Add document data to array
            });
            
            console.log(`Documents retrieved from Firestore: `, documents);
            response.send(documents);                 // documents data as JSON array
        })
        .catch(error => {
        console.error(`Error getting collection from Firestore: `, error);
        response.status(500).send(error);
        });
});


/**
 * This function retrieves an array of documents, each document from 'business' collection.
 * @param HTML request which contains collection ID and business name.
 * @returns HTML response which contains an array of business documents, each document in json format.
 */
exports.getBusinessBySubNameFromFirestore = functions.https.onRequest((request, response) => {
    console.log('Starting \`getBusinessBySubNameFromFirestore\` execution.');

    const collectionPath = request.query.collection;  // Collection path
    const businessName = request.query.businessname;  // Document ID

    console.log(`collection: ${collectionPath}`);
    console.log(`business_name: ${businessName}`);

    admin.firestore().collection(collectionPath)
                     .where('business_name', '>=', `${businessName}`).where('business_name', '<=', `${businessName}\uf8ff`).get()
        .then(snapshot => {
            var documents = [];                       // Array to store document data
            
            snapshot.forEach(doc => {
                documents.push({doc : doc.data()});  // Add document data to array
            });
            
            console.log(`Documents retrieved from Firestore: `, documents);
            response.send(documents);                 // documents data as JSON array
        })
        .catch(error => {
        console.error(`Error getting collection from Firestore: `, error);
        response.status(500).send(error);
        });
});


/**
 * This function retrieves an entire collection from firestore.
 * @param HTML request which contains collection ID and business name.
 * @returns HTML response which contains an array of business documents, each document in json format.
 */
exports.getCollectionFromFirestore = functions.https.onRequest((request, response) => {
    console.log('Starting \`getCollectionFromFirestore\` execution.');

    const collectionPath = request.query.collection;  // Collection path

    admin.firestore().collection(collectionPath).get()    
        .then(snapshot => {
            var documents = [];                       // Array to store document data
            
            snapshot.forEach(doc => {
                documents.push({data : doc.data()});  // Add document data to array
            });
            
            console.log(`Documents retrieved from Firestore: `, documents);
            response.send(documents);                 // documents data as JSON array
        })
        .catch(error => {
        console.error(`Error getting collection from Firestore: `, error);
        response.status(500).send(error);
        });
});


/**
 * This function updates a document in firestore.
 * @param HTML request which contains data in json format.
 * @returns HTML response which indicates success or failure.
 */
exports.updateDocumentInFirestore = functions.https.onRequest((request, response) => {
    console.log('Starting \`updateDocumentInFirestore\` execution.');

    const collectionPath = request.query.collection;  // Collection path
    const docId = request.query.username;             // Document ID
    const data = request.body;                        // Data to be added to Firestore

    admin.firestore().collection(collectionPath).doc(docId).update(data)
        .then(doc => {
        console.log(`Document with ID: ${doc.id} updated in Firestore collection "${collectionPath}"`);
        response.send(`Document with ID: ${doc.id} updated in Firestore collection "${collectionPath}"`);
        })
        .catch(error => {
        console.error(`Error updating data in Firestore: `, error);
        response.status(500).send(error);
        });
});