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
			//On cr�e une ArrayList pour contenir tout les fichiers (ne pas les afficher directement au cas ou il y en a trop) 
			ArrayList<File> test = new ArrayList<File>();
			//On initialise le compteur;
			int compteur = 0;
			//On initialise la variable d'arr�t
			Boolean stop = false;
			//On cr�e la liste des formats � chercher
			//POUR L'INSTANT HARDCODE (� changer)
			String[] formats = new String[2];
			
			//A CHANGER !!
			formats[0] = ".vcf"; formats[1] = ".ics";
			//On r�cup�re la liste des fichiers de ces formats ci
			test = Directory_Management.listFileFormats(formats, directory, true);
			
			Iterator<File> iterator = test.iterator();
			while(iterator.hasNext() && !stop) {
				//On r�cup�re le nom du fichier et on l'affiche
				String fileName = iterator.next().getName();
				System.out.println(fileName);
				//On incr�mente le compteur
				compteur++;
				//Si le compteur est �gal � la taille qu'on veut, on arr�te l'iterator
				if(size != -1 && compteur>=size) stop = true;
			}
			
		}
		
		//M�thode qui permet de savoir si l'argument suivant est un "-param�tre" ou un argument valide (exemple un fichier de sortie, un dossier � analyser etc...)
		public static Boolean isValidArgument(String[] args, int argIndex) {		
			//On initialise le compteur d'argument valides suivant (on ne v�rifie pas le type d'argument pr�cis, seulement si c'est un param�tre ou non)
			int argCount= argIndex;
			//Si le compteur d'arguments est inferieur ou �gal � l'index de l'argument suivant (arg[1] a pour index 2), on retourne false
			if(args.length <= argIndex) return false;
			//Sinon on analyse les arguments suivants
			else {
				//Tant qu'on ne trouve pas que l'argument suivant est un "-param�tre" ou que l'argument suivant n'existe pas, on incr�mente le compteur
				//args.length > argCount en condition car le compteur d'arguments doit �tre sup�rieur strictement � l'index de l'argument (m�me r�gle qu'au dessus)
				while(args.length > argCount && !args[argCount].startsWith("-")) {
					argCount++;
				}
				//Si le compteur est �gal � l'index de l'argument, dans ce cas l'agument est invalide
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
					+"-M		MAX: le nombre de fichiers maxium � afficher.\n\r"
					+"-d		DIRECTORY: le prochain argument est le dossier �\n\r"
					+"			partir duquel on liste les fichiers.\n\r"
					+"-t		TYPE: le type de fichiers � afficher dans le mode\n\r"
					+"			Display.\n\r"
					+"-i		INPUT: le prochain argument est le fichier d'entr�e\n\r"
					+"			du logiciel.\n\r"
					+"-o		OUTPIUT: le prochain argument est le fichier de\n\r"
					+"			sortie du logiciel.\n\r"
					+"-om		OUTPUT MULTIPLE: les prochains arguments sont les\n\r"
					+"			fichiers de sortie � g�n�rer, plusieurs formats\n\r"
					+"			peuvent �tre g�n�r�s en m�me temps.\n\r"
					+"-H		HTML: le prochain argument est le fichier HTML\n\r"
					+"			de sortie du logiciel.\n\r"
					);
		}
		
}
