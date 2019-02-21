/*
    Written on: 2/21/2019.
    All the examples methods works with OWL API 5 as of this writing.
    Note: Some of the methods used in the examples are depreciate.

    Reminder:
    The repeated declaration of OWLOntologyManager, OWLOntology classes and other classes were intentional for practice.
    All these resources could be declared static to the Class instead of declaring them in each method. Which would lead to a lot efficient application.

    Tutorial URL: http://syllabus.cs.manchester.ac.uk/pgt/2017/COMP62342/introduction-owl-api-msc.pdf

 */

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.formats.FunctionalSyntaxDocumentFormat;
import org.semanticweb.owlapi.formats.ManchesterSyntaxDocumentFormat;
import org.semanticweb.owlapi.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Map;

public class OWLAPIFirst {
    @SuppressWarnings("deprecation")
    public static void main(String[] args) {
        //Creating the object to call the methods:
        try {
            OWLAPIFirst owlApifirst = new OWLAPIFirst();

            // Calling the initial tasks.
            // owlApifirst.initial_tasks();

            // Test 1:
            // owlApifirst.test1();

            //Axioms and Entities
            //  owlApifirst.axiomsAndEntities();

            //Changing Axioms
            owlApifirst.changeAxioms();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initial_tasks() throws FileNotFoundException, OWLOntologyStorageException {
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
            /*
                Uncomment the below lines to see it working.
                Since, the ontologies used in the Task 3 and Task 2 are same, it throws an OntologyAlreadyExistsException.

             */
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

            // Note:
            /*
               An ontology always carries a reference of the OWLOntologyManager.
               It is often required in methods to pass the ontology and it's manager as parameters.
               Instead of looking for the OWLOntologyManager object, we can just use the below method to send the reference:
                <your_ontology>.getOWLOntologyManager()
              */

        }catch(OWLOntologyCreationException e) {
            e.printStackTrace();
        }
    }

    public void test1() {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntologyManager man2 = OWLManager.createOWLOntologyManager();
        OWLOntology familyOntology;
        OWLOntology newFam;
        try {

            File file = new File("famOnto.owl");
            IRI famIRI = IRI.create("http://www.cs.man.ac.uk/~stevensr/ontology/family.rdf.owl");
            familyOntology = man.loadOntology(famIRI);
            man.saveOntology(familyOntology, new ManchesterSyntaxDocumentFormat(), new FileOutputStream(file));
            newFam = man2.loadOntologyFromOntologyDocument(file);
            System.out.println("AxiomsL " + newFam.getAxiomCount() + ", Format: " + man2.getOntologyFormat(familyOntology));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void axiomsAndEntities() throws OWLOntologyCreationException {
        /*
            There are two building blocks of OWL:
                1. Entities: Entities can be OWLIndividuals(Concrete item in domain), OWLClass(Sets of Individuals) or OWLObjectProperty(Relationship between individuals)
                2. Axioms: Statements about the Entities. Ex: TomCruise isPartOf KnightAndDay

               Ontologies should be viewed as set of axioms.
               The classes and properties cannot be "just added" to the ontology.
               They need to be binded in an Aziom. This Axiom is later added to the ontology using one of the following methods:
               1. Using the OWLOntology class add(owlDeclarationAxiom) method
               2. Using OWLOntologyManager class addAziom(ontology, owlDeclaratiomAxiom) method.
               3. Using OWLOntologyManager class applyChange(addAxiom) method. This is how OWL API handles any changes to the OWL Azioms internally.
                        AddAxiom ax = new AddAxiom(ontology, owlDeclarationAxiom);
                        owlOntologyManager.applyChanges(ax);
         */
        // Task 1: Declaring an Axiom in the ontology.
        IRI iri = IRI.create("http://owl.api.tutorial");
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology onto = man.createOntology(iri);
        OWLDataFactory df = onto.getOWLOntologyManager().getOWLDataFactory();
        OWLClass person = df.getOWLClass(iri + "#Person");
        OWLClass woman = df.getOWLClass(iri + "#Woman");
        /*
            While adding a proper Axiom (Ex; Using SubClassOF axiom) OWL API automatically adds all the OWLDeclarationAxiom declarations.
            Hence, you barely need to declare them manually.
         */
//        OWLDeclarationAxiom oda = df.getOWLDeclarationAxiom(person);
//        onto.add(oda);
//        System.out.println(onto);

        OWLSubClassOfAxiom relation = df.getOWLSubClassOfAxiom(woman, person);
        onto.add(relation);

        //Creating more complex Class Expressions
        OWLClass A = df.getOWLClass(iri + "#A");
        OWLClass B = df.getOWLClass(iri + "#B");
        OWLClass C = df.getOWLClass(iri + "#C");

        OWLObjectProperty R = df.getOWLObjectProperty(iri + "#R");
        OWLObjectProperty S = df.getOWLObjectProperty(iri + "#S");

        OWLSubClassOfAxiom ax1 = df.getOWLSubClassOfAxiom(df.getOWLObjectSomeValuesFrom(R, A), df.getOWLObjectSomeValuesFrom(S, B));
        onto.add(ax1);
        //  onto.logicalAxioms().forEach(System.out::println);

        //Deleting a axiom from the ontology.
        OWLClass men = df.getOWLClass(iri + "#Man");
        OWLSubClassOfAxiom mSubClassOfWomen = df.getOWLSubClassOfAxiom(men, woman); //This is wrong. Man is not a subclass of women.

        //Adding a Axiom
        onto.add(mSubClassOfWomen);
        System.out.println("Axioms :" + onto.getSignature());

        //Deleting a Axiom. It is important to note that the Axiom must be recreated in code in order to be able to delete them.
        /*
            Just as adding the ontologies, there are 3 different ways to remove an ontology.
                1. Using OWLOntologyManager class removeAxiom(ontology,axiom)
                2. Using RemoveAxiom class Ex: RemoveAxiom ra = new RemoveAxiom(ontology, axiom);
                3. Using OWLOntologyManager class applyChnage(remoceAxiom) method;
         */
        onto.remove(mSubClassOfWomen);
        System.out.println("Axioms :" + onto.getSignature());

    }

    public void changeAxioms() throws OWLOntologyCreationException {
        /*
            The Ma<key,value> : key represents the OWLClassExpressions to be replaced
                                value represents the OWLClassExpressions to be replaced with.

            The OWLObjectTransformer then takes in the map and performs all the replacements and returns the set of OWLObjectChanges which can then be applied to ontologies.
            The OWLObjectTransformer class is generic. Hence, we can perform replacement for any OWLEntity.

            Traversing through the Ontology:
                Traditionally:
                    for(OWLAxiom axiom : onto.getLogicalAxiom()){
                        System.out.println(axiom);
                    }
                 Note: getLogicalAxiom() is depricated.

                Newer ways of traversal uses Java8's Stream. Often performs better than the traditional way.
                onto.logicalAxiom().forEach(System.out::println);
         */

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology onto;
        OWLDataFactory df;
        IRI iri = IRI.create("http://owl.api.tutorial");
        onto = man.createOntology(iri);
        df = onto.getOWLOntologyManager().getOWLDataFactory();

        final Map<OWLClassExpression, OWLClassExpression> replacements = new HashMap<>();

        OWLClass A = df.getOWLClass(iri + "#A");
        OWLClass B = df.getOWLClass(iri + "#A");
        OWLClass X = df.getOWLClass(iri + "#A");
        OWLObjectProperty R = df.getOWLObjectProperty(iri + "#R");
        OWLObjectProperty S = df.getOWLObjectProperty(iri + "#S");

        OWLSubClassOfAxiom ax = df.getOWLSubClassOfAxiom(df.getOWLObjectSomeValuesFrom(R, A), df.getOWLObjectSomeValuesFrom(S, B));
        onto.add(ax);

        //The following line of code does not seem to work. "Usage reference not supported at language level:5"
        //The following line is making use of JAVA*'s stream.
        // More on stream at : https://winterbe.com/posts/2014/07/31/java8-stream-tutorial-examples/
        //onto.logicalAxioms().forEach(System.out::println);

        replacements.put(df.getOWLObjectSomeValuesFrom(R, A), X);
//        applyChanges() is unable to process a List<OWLOntologyChange> for some reason. Needs more research.
//        OWLObjectTransformer<OWLClassExpression> replacer =
//                    new OWLObjectTransformer<>((x) -> true,(input) -> {
//                        OWLClassExpression l = replacements.get(input);
//                        if(l==null){
//                            return input;
//                        }
//                        return l;
//                    },df,OWLClassExpression.class);
//
//        List<OWLOntologyChange> results = replacer.change(onto);
//        onto.applyChange(results);
    }


}
