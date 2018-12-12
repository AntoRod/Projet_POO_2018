package file_management;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Argument_Management {

	/*	CETTE CLASS CONTIENT LES METHODES RELATIVES A LA GESTION DES ARGUMENTS
	 *		- VERIFIER LA VALIDITE D'UN ARGUMENT
	 *		- TRAITER LES ARGUMENTS
	 */
	
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
			test = Directory_Management.listFileFormats(formats, directory, true);
			
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
		public static Boolean isValidArgument(String[] args, int argIndex) {		
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
		public static void displayManual() {
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
