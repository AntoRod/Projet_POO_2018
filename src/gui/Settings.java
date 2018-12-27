package gui;

import java.util.HashMap;

public class Settings {
	//CETTE CLASS CONTIENT LES SETTINGS POTENTIELS A UTILISER
	
	
	//La HashMap des noms pour les JLabel de la Vcard
	public static HashMap<String, String> vcard_name_map = new HashMap<String, String>();
	public static HashMap<String, String> getVcardMap() {
		return vcard_name_map;
	}
	public static HashMap<String, String> vcalendar_name_map = new HashMap<String, String>();
	public static HashMap<String, String> getVcalendarMap() {
		return vcalendar_name_map;
	}
	public static void setVcardNameMap() {
		//Ne contient que les valeurs à AFFICHER dans le GUI, certaines ne sont pas nécéssaires à afficher (à enlever donc ?)
		vcard_name_map.put("_name","Nom");
		vcard_name_map.put("_firstName","Prénom");
		vcard_name_map.put("_homePhone","Tel Perso");
		vcard_name_map.put("_mail","Email");
		vcard_name_map.put("_homeAdress","Adresse Maison");
		vcard_name_map.put("_company","Entreprise");
		vcard_name_map.put("_workPhone","Tel Travail");
		vcard_name_map.put("_workAdress","Adresse Travail");
		vcard_name_map.put("_title","Poste");
		vcard_name_map.put("_picture","Photo");
	}
	public static void setVcalendarNameMap() {
		vcalendar_name_map.put("_dateBegin","Date de Début");
		vcalendar_name_map.put("_dateEnd","Date de Fin");
		vcalendar_name_map.put("_summary","Résumé");
		vcalendar_name_map.put("_categories","Catégories");
		vcalendar_name_map.put("_transparency","Transparence");
		vcalendar_name_map.put("_location","Localisation");
		vcalendar_name_map.put("_description","Description");
	}
	
	
	
	
	
	//La HashMap des noms pour les JLabel du Vcalendar
	
	
	
	
	
	
	
	
	
	
	

}
