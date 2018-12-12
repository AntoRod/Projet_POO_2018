package tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import datas.Vcard;
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
 */


public class test_Vcards {
												//Défini si le maximum de fichiers à afficher est initialisé
	private static Boolean						_isMaximumSet = false;
	private static int							_maximumArg;
												//Défini si un dossier spécifique à afficher est initialisé en entrée (avec affichage des formats voulu)
	private static Boolean						_isDisplaySet = false;
	private static int							_displayArg;
												//Défini si un type de fichier à afficher a été spécifié (peut en contenir plusieurs)
	private static Boolean						_isTypeOfDisplaySet;
	private static String[]						_typeOfFiles = null;
												//Défini si un fichier est initialisé en entrée
	private static Boolean						_isInputSet = false;
	private static int							_inputArg;
												//Défini si un fichier est initialisé en sortie
	private static Boolean						_isOutputSet = false;
	private static int							_outputArg;
												//Défini si un fichier HTML est initialisé en sortie
	private static Boolean						_isHTMLSet = false;
	private static int							_HTMLArg;
												//Défini si plusieurs fichiers sont initialisés en sortie
	private static Boolean						_isMultipleOutputSet;
	private static int[]						_multipleOutputArg;
												//Défini si une erreur est entrée en paramètre
	private static Boolean						_isErrorSet;
	private static String						_errorType = null;

	public static void main(String[] args) {
		
		
		/*
		 *	PARTIE GESTION DES ARGUMENTS
		 *		- ANALYSE DES ARGUMENTS
		 *		- TRAITEMENT DES ARGUMENTS
		 */
		
		//ANALYSE DES ARGUMENTS
		int maximum = -1;
		if(args.length == 0) displayManual();
		
		if(args.length != 0) {
			//Le nombre maximum de fichiers à afficher
			
			//On analyse les arguments
			for(int i=0;i<args.length;i++) {
				//Si l'argument est -M: la taille maximale d'affichage des fichiers
				if("-M".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(isValidArgument(args, i+1)) {
						_isMaximumSet = true;
						_maximumArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType += "MAXIMUM;";
					}
				}
				//Si l'argument est -d: on lance la méthode qui liste les fichiers dans le répertoire (argument suivant)
				if("-d".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(isValidArgument(args, i+1)) {
						_isDisplaySet = true;
						_displayArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType += "DISPLAY;";
					}
				}
				if("-i".equals(args[i])) {
					//Si l'argument suivant est défini correctement (n'est pas vide ou n'est pas un "-paramètre")
					if(isValidArgument(args, i+1)) {
						_isInputSet = true;
						_inputArg = i+1;
					}
					//Sinon avertir d'une erreur
					else {
						_isErrorSet = true;
						_errorType +="INPUT;";
					}
				}
			}
		}
		
		
		//TRAITEMENT DES ERREURS
		if(_errorType != null) {
			//Si on a une erreur de display, on affiche le message d'erreur, le cas d'erreur est traité automatiquement dans l'affichage (affichage par défaut)
			if(_errorType.contains("DISPLAY")) {
				System.out.println("Bad display argument, displaying default location");
				displayVcardsInDirectory("./", _typeOfFiles, maximum);
			}
			//Si on a une erreur d'input, on affiche le message d'erreur, on ne peut pas afficher le contenu d'input dans ce cas ci (pas de cas par défaut)
			if(_errorType.contains("INPUT")) System.out.println("Bad input argument, can't import a proper file");
		}
		
		//TRAITEMENT DES ARGUMENTS
		//Si le maximum est défini
		if(_isMaximumSet) {
			maximum = Integer.parseInt(args[_maximumArg]);
		}
		//Si le mode display est défini
		if(_isDisplaySet) {
			displayVcardsInDirectory(args[_displayArg], _typeOfFiles, maximum);
		}
		if(_isInputSet) {
			Vcard test_Card = new Vcard();
			test_Card.analyzeFile(args[_inputArg]);
			System.out.println(test_Card);
		}
		


		
		
		/*FIN PARTIE GESTION DES ARGUMENTS*/

		
		//Vcard test_Card = new Vcard();
		//test_Card.analyzeFile(new File("test_card.vcf"));
		//System.out.println(test_Card);

	}
	
	//Gestion de l'affichage des Vcards
	public static void displayVcardsInDirectory(String directory,String[] typeOfFiles, int size) {
		//On crée une ArrayList pour contenir tout les fichiers (ne pas les afficher directement au cas ou il y en a trop) 
		ArrayList<File> test = new ArrayList<File>();
		//On initialise le compteur;
		int compteur = 0;
		//On initialise la variable d'arrêt
		Boolean stop = false;
		//On crée la liste des formats à chercher
		//POUR L'INSTANT HARDCODE (à changer)
		String[] formats = new String[2];
		
		//A CHANGER !!
		formats[0] = ".vcf"; formats[1] = ".ics";
		//On récupère la liste des fichiers de ces formats ci
		test = new Directory_Management().listFileFormats(formats, directory, true);
		
		Iterator<File> iterator = test.iterator();
		while(iterator.hasNext() && !stop) {
			//On récupère le nom du fichier et on l'affiche
			String fileName = iterator.next().getName();
			System.out.println(fileName);
			//On incrémente le compteur
			compteur++;
			//Si le compteur est égal à la taille qu'on veut, on arrête l'iterator
			if(size != -1 && compteur>=size) stop = true;
		}
		
	}
	
	//Méthode qui permet de savoir si l'argument suivant est un "-paramètre" ou un argument valide (exemple un fichier de sortie, un dossier à analyser etc...)
	private static Boolean isValidArgument(String[] args, int argIndex) {		
		//On initialise le compteur d'argument valides suivant (on ne vérifie pas le type d'argument précis, seulement si c'est un paramètre ou non)
		int argCount= argIndex;
		//Si le compteur d'arguments est inferieur ou égal à l'index de l'argument suivant (arg[1] a pour index 2), on retourne false
		if(args.length <= argIndex) return false;
		//Sinon on analyse les arguments suivants
		else {
			//Tant qu'on ne trouve pas que l'argument suivant est un "-paramètre" ou que l'argument suivant n'existe pas, on incrémente le compteur
			//args.length > argCount en condition car le compteur d'arguments doit être supérieur strictement à l'index de l'argument (même règle qu'au dessus)
			while(args.length > argCount && !args[argCount].startsWith("-")) {
				argCount++;
			}
			//Si le compteur est égal à l'index de l'argument, dans ce cas l'agument est invalide
			if(argCount == argIndex) return false;
			//Sinon on retourne true car le compteur d'argument est valide
			else return true;
		}

	}
	
	//Affichage du mode d'emploi du logiciel
	private static void displayManual() {
		System.out.println
				(""
				+"Mode d'Emploi:\n\r"
				+"-M		MAX: le nombre de fichiers maxium à afficher.\n\r"
				+"-d		DIRECTORY: le prochain argument est le dossier à\n\r"
				+"			partir duquel on liste les fichiers.\n\r"
				+"-t		TYPE: le type de fichiers à afficher dans le mode\n\r"
				+"			Display.\n\r"
				+"-i		INPUT: le prochain argument est le fichier d'entrée\n\r"
				+"			du logiciel.\n\r"
				+"-o		OUTPIUT: le prochain argument est le fichier de\n\r"
				+"			sortie du logiciel.\n\r"
				+"-om		OUTPUT MULTIPLE: les prochains arguments sont les\n\r"
				+"			fichiers de sortie à générer, plusieurs formats\n\r"
				+"			peuvent être générés en même temps.\n\r"
				+"-H		HTML: le prochain argument est le fichier HTML\n\r"
				+"			de sortie du logiciel.\n\r"
				);
	}
}
