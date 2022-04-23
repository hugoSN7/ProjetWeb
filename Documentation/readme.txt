Find here all the documentation about our project

Pour React avec Jboss:
  partie React:
    // install nodejs
    // download and untar archive
    export PATH=$HOME/install/node-v16.14.0-linux-x64/bin:$PATH

    // create react app
    npx create-react-app my-react-app
    cd my-react-app

    // put index.js and Main.js in src

    // allow proxying to handle cors
    // edit package.json :
    // "proxy" : "http://localhost:8080/",

    // start react server
    npm start

    // start rest server (JBoss + Annuaire rest app)

  partie jboss:
    -creer un porjet web dynamic
    -ajouter les jar utilisé ds les tp
    -ajouter les jar suivant egalement:
      EAP-7.0.0/modules/system/layers/base/com/fasterxml/jackson/core/jackson-annotations/main/jackson-annotations-2.5.4.redhat-1.jar
      EAP-7.0.0/modules/system/layers/base/javax/ws/rs/api/main/jboss-jaxrs-api_2.0_spec-1.0.0.Final-redhat-1.jar
    -depuis le projet annuaireJQ:
      - the Java sources in a pack package
      - persistence.xml in a src/META-INF folder
      - the HTML pages + Control.js in the WebContent folder
      - web.xml in the WebContent/WEB-INF folder
   
Lancer jboss et react
      
