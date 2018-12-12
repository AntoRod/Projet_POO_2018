package file_management;

import java.io.File;
import java.util.ArrayList;

public class Directory_Management {
	
	/*
	 *	CETTE CLASS CONTIENT TOUTES LES METHODES DE GESTION DE FICHIERS
	 *		- AFFICHAGE DES FICHIERS DE TYPE SPECIFIQUES CONTENUS DANS UN DOSSIER (AVEC OU SANS SOUS DOSSIER)
	 *		- AFFICHAGE DU CONTENU D'UN FICHIER ?? (d'une liste ??)
	 *
	 */
	
	//M�thode qui permet de lister tout les fichiers d'une liste de format sp�cifiques dans un dossier sp�cifique, avec ou sans sous dossier
	public static ArrayList<File> listFileFormats(String[] formats, String directory, Boolean subDirectories) {
		//On fait la liste de tout les dossiers/fichiers pr�sents dans le r�pertoire
		File repository = new File(directory);
//		System.out.println(repository.getAbsolutePath());
		File[] files = repository.listFiles();
		
		ArrayList<File> properFiles = new ArrayList<File>();
		//Si cette liste est non vide, on l'analyse
		if(files != null) {
			for(int i=0;i<files.length;i++) {
				//Si c'est un fichier, on regarde son extension
				if(files[i].isFile()) {
					for(int j=0;j<formats.length;j++) {
						//On analyse les formats et si c'est un des formats demand�s, on l'ajoute � la liste
//						System.out.println(files[i].getName());
						if(files[i].getName().endsWith(formats[j])) properFiles.add(files[i]);
					}
				}
				//Sinon si c'est un dossier, l'analyser si l'option est activ�e
				else if(files[i].isDirectory()) {
					if(subDirectories) properFiles.addAll(listFileFormats(formats, files[i].getName(), subDirectories));
				}
			}
		}
		//On retourne la liste des fichiers correspondant � nos crit�res
		return properFiles;
	}
	
	

}
