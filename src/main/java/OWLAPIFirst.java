
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class OWLAPIFirst {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) throws FileNotFoundException, OWLOntologyStorageException {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology o;
        OWLOntology pizzaOntology;
        OWLOntology webPizzaOntology;
        try {
            //Task 1: Creating an empty ontology.
			/*	Creating an empty ontology.
				Up on creation it will be assigned an Anonymous ID(Anonymous-0)
				A ton of other information like Axioms, Logical Axioms are shown when it is printed to the standard output.
			*/

            o = man.createOntology();
            System.out.println("Info related to first Ontology: " + o);

            //Task 2: Opening an existing ontology from the file system.
            File file = new File("pizza.owl");
           // pizzaOntology = man.loadOntologyFromOntologyDocument(file);
          //  System.out.println("Info related to Pizza Ontology: "+pizzaOntology);

            //Task 3: Loading an ontology from the web.
            IRI pizzaIRI = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
            webPizzaOntology = man.loadOntology(pizzaIRI);
            System.out.println("Info related to web pizza ontology: " + webPizzaOntology);

            //Task 4. Saving an ontology to the disk.
            /*
             * 	The ontologies can be saved in many formats example. OWL/XML, Latex, Functional Syntax(One used below)
             * 	For more information about the different formats of storing the ontologies check the type hierarchy of OWLDocumentFomatImpl(Ctrl + T) for type hierarchy.
             * */
            File outputFile = new File("pizzaFunctionalSyntax.owl");
            man.saveOntology(webPizzaOntology, new FunctionalSyntaxDocumentFormat(),new FileOutputStream(outputFile));
        }catch(OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

}
