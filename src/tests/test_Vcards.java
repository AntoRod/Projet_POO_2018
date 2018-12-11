package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

//import datas.*;
import file_management.*;

/*
 * MODE CONSOLE: 
 * 			- AUCUN ARGUMENT: FONCTIONNEL
 * 			- GESTION DES ARGUMENTS: EN COURS
 * 			- TRAITEMENT DU FICHIER VCARD EN ENTREE: EN COURS
 * 			- TRAITEMENT DU FICHIER SERIALISE DE LA VCARD EN SORTIE: EN COURS
 * 			- AFFICHAGE DES VCARD: EN COURS
 * 			- AFFICHAGE DE LA LISTE DES VCARDS: FONCTIONNEL
 * 
 * 
 * 
 * 
 * 
 * 
 * 
 */


public class test_Vcards {

	public static void main(String[] args) {
		
		
		/*PARTIE GESTION DES ARGUMENTS*/
		if(args.length == 0) {displayManual();}
		
		if(args.length != 0) {
			//Le nombre maximum de fichiers à afficher
			int maximum = -1;
			//On analyse les arguments
			for(int i=0;i<args.length;i++) {
				//Si l'argument est -M: la taille maximale d'affichage des fichiers
				if("-M".equals(args[i])) {
					maximum = Integer.parseInt(args[i+1]);
				}
				//Si l'argument est -d: on lance la méthode qui liste les fichiers dans le répertoire (argument suivant)
				if("-d".equals(args[i])) {
					displayVcardsInDirectory(args[i+1], maximum);
				}
			}
		}
		
		/*FIN PARTIE GESTION DES ARGUMENTS*/

		
		//Vcard test_Card = new Vcard();
		//test_Card.analyzeFile(new File("test_card.vcf"));
		//System.out.println(test_Card);

	}
	
	//Gestion de l'affichage des Vcards
	public static void displayVcardsInDirectory(String directory, int size) {
		//On crée une ArrayList pour contenir tout les fichiers (ne pas les afficher directement au cas ou il y en a trop) 
		ArrayList<File> test = new ArrayList<File>();
		//On initialise le compteur;
		int compteur = 0;
		Boolean stop = false;
		//On crée la liste des formats à chercher
		String[] formats = new String[2];
		formats[0] = ".vcf"; formats[1] = ".ics";
		//On récupère la liste des fichiers de ces formats ci
		test = new Directory_Management().listFileFormats(formats, directory, true);
		Iterator<File> iterator = test.iterator();
		while(iterator.hasNext() && !stop) {
			String fileName = iterator.next().getName();
			System.out.println(fileName);
			compteur++;
			if(size != -1 && compteur>=size) stop = true;
		}
		
		
		
	}
	
	//Affichage du mode d'emploi du logiciel
	public static void displayManual() {
		System.out.println(""
				+"Mode d'Emploi:\n\r"
				+"-d		DIRECTORY: le prochain argument est le dossier à\n\r"
				+"			partir duquel on liste les fichiers.\n\r"
				+"-i		INPUT: le prochain argument est le fichier d'entrée\n\r"
				+"			du logiciel.\n\r"
				+"-o		OUTPIUT: le prochain argument est le fichier de\n\r"
				+ "			sortie du logiciel.\n\r"
				+"-h		HTML: le prochain argument est le fichier HTML\n\r"
				+"			de sortie du logiciel.\n\r"
				+"-M		MAX: le nombre de fichiers maxium à afficher.\n\r"
				);
	}
}
