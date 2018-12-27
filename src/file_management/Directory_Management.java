package file_management;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class Directory_Management {
	
	/*
	 *	CETTE CLASS CONTIENT TOUTES LES METHODES DE GESTION DE FICHIERS
	 *		- AFFICHAGE DES FICHIERS DE TYPE SPECIFIQUES CONTENUS DANS UN DOSSIER (AVEC OU SANS SOUS DOSSIER)
	 *		- AFFICHAGE DU CONTENU D'UN FICHIER ?? (d'une liste ??)
	 *
	 *
	 */
	/**
	 * 
	 * @param formats The formats we want to display.
	 * @param directory The directory we want to analyze.
	 * @param subDirectories If we want to analyze the subDirectories or not.
	 * @return Return an ArrayList of the files we want to display.
	 */
	//M�thode qui permet de lister tout les fichiers d'une liste de format sp�cifiques dans un dossier sp�cifique, avec ou sans sous dossier
	public static ArrayList<String> listFileFormats(ArrayList<String> formats, String directory, Boolean subDirectories) {
		//On fait la liste de tout les dossiers/fichiers pr�sents dans le r�pertoire
		File repository = new File(directory);
		File[] files = repository.listFiles();
		
		ArrayList<String> properFiles = new ArrayList<String>();
		//Si cette liste est non vide, on l'analyse
		if(files != null) {
			for(int i=0;i<files.length;i++) {
				//Si c'est un fichier, on regarde son extension
				if(files[i].isFile()) {
					Iterator<String> formatIterator = formats.iterator();
					while(formatIterator.hasNext()) {
						String format = formatIterator.next();
						//On analyse les formats et si c'est un des formats demand�s, on l'ajoute � la liste
//						System.out.println(files[i].getName());
						if(files[i].getName().endsWith(format)) {
							try {
								properFiles.add(files[i].getCanonicalPath());
							} catch (IOException e) {e.printStackTrace();}
								

						}
					}
				}
				//Sinon si c'est un dossier, l'analyser si l'option est activ�e
				else if(files[i].isDirectory()) {
					if(subDirectories) {
						ArrayList<String> subDirList = listFileFormats(formats, files[i].getAbsolutePath(), subDirectories);
						properFiles.addAll(subDirList);
					}
				}
			}
		}
		//On retourne la liste des fichiers correspondant � nos crit�res
		return properFiles;
	}
	/**
	 * 
	 * @param fileName The file in which we write the HTML Page
	 * @param HTMLPage The String that contains all the HTML Code we want to write.
	 */
	//M�thode qui permet de convertir un String HTML valide en un fichier HTML 5 complet et valide (strict minimum) � partir du nom du fichier et du code g�n�r� sous forme de String
	public static void convertStringToHTMLFile(String fileName, String HTMLPage) {
		try {
			FileWriter HTMLWriter = new FileWriter(new File(fileName));
			HTMLWriter.append(HTMLPage);
			
			HTMLWriter.flush();
			HTMLWriter.close();
		} catch (IOException e) {e.printStackTrace();}
	}
	
	//M�thode qui permet de d�s�rialiser un objet g�n�rique
	public static Object readSerialisedObject (String fileName) {
		Object object = null;
		try {
			//On cr�e notre inputStream
			ObjectInputStream inputVcard = new ObjectInputStream(new FileInputStream(fileName));
			//On remet la Vcard � jour avec les nouvelles informations
			object = inputVcard.readObject();
			//On oublie pas de fermer le stream
			inputVcard.close();
		} catch (FileNotFoundException e) {e.printStackTrace();}
			catch (IOException e) {e.printStackTrace();} 
				catch (ClassNotFoundException e) {e.printStackTrace();}
		return object;
	}
	/**
	 * 
	 * @param fileName The name of the File we want to extract the extension from.
	 */
	//M�thode qui retourne l'extension du fichier s�l�ctionn�
	public static String getFileExtension(String fileName) {
		return getFileExtension(new File(fileName));
	}
	public static String getFileExtension(File file) {
		//On s�pare le fichier pour obtenir son extension
		String[] split = file.getName().split("\\.");
		//On retourne le dernier �l�ment pour obtenir l'extension
		return split[split.length-1];
	}

}
