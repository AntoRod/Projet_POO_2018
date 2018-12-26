package file_management;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Argument_Management {

	/*	CETTE CLASS CONTIENT LES METHODES RELATIVES A LA GESTION DES ARGUMENTS
	 *		- VERIFIER LA VALIDITE D'UN ARGUMENT
	 *		- TRAITER LES ARGUMENTS
	 *
	 */
	
	/**
	 * 
	 * @param directory The directory from which we display the elements.
	 * @param typeOfFiles The type of Elements we want to display.
	 * @param size The maximum number of Files to display.
	 */
	//M�thode qui affiche le nom des fichiers contenus dans le dossier voulu, les types de fichier � afficher sont pass�s en param�tres (si null, affichage des types par d�faut)
	public static void displayElementsInDirectory(String directory, ArrayList<String> typeOfFiles, int size) {
		//On cr�e une ArrayList pour contenir tout les fichiers (ne pas les afficher directement au cas ou il y en a trop) 
		ArrayList<File> test = new ArrayList<File>();
		//On initialise le compteur;
		int compteur = 0;
		//On initialise la variable d'arr�t
		Boolean stop = false;
		//On r�cup�re la liste des fichiers de ces formats ci
		test = Directory_Management.listFileFormats(typeOfFiles, directory, true);
		
		Iterator<File> iterator = test.iterator();
		while(iterator.hasNext() && !stop) {
			//On r�cup�re le nom du fichier et on l'affiche
			String fileName = iterator.next().getName();
			System.out.println(fileName);
			//On incr�mente le compteur
			compteur++;
			//Si le compteur est �gal � la taille qu'on veut, on arr�te l'iterator
			//Sinon on laisse tourner car maximum = -1 par d�faut, c'est le cas ou on ne sp�cifie pas de maximum
			if(size != -1 && compteur>=size) stop = true;
		}
		
	}
	/**
	 * 
	 * @param args The arguments we want to analyze
	 * @param argIndex The specified index of the argument we want to analyze
	 * @return Return the validity of the argument we analyzed	 */
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

	/**
	 * Display the Argument Manual to help you figure out how to use the jar file
	 */
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
				+"-h		HTML: le prochain argument est le fichier HTML\n\r"
				+"			de sortie du logiciel.\n\r"
				+"-p		PAGE: le prochain argument est un fichier HTML 5\n\r"
				+ "			minimal mais Valide qui sera cr��."
				);
	}
		
}
