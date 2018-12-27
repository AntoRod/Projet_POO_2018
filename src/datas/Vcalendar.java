package datas;

import java.io.Serializable;

public class Vcalendar implements Serializable{
	
	private static final long serialVersionUID = 8303974458230502084L;
	//VCALENDAR DE TYPE VEVENT SEULEMENT ?
												//La date de début du Vevent
	private String								_dateBegin;
												//La datte de fin du Vevent
	private String								_dateEnd;
												//Le résumé du Vevent
	private String								_summary;
												//Les catégories du Vevent
	private String								_categories;
												//si la ressource affectée à l'événement est rendu indisponible
	private String								_transparency;
												//L'endroit ou a lieu le Vevent
	private String								_location;
												//La description du Vevent
	private String								_description;
	
	
	/**
	 * Initializes a newly created Vcalendar.
	 */
	public Vcalendar() {
	}
	
	/*GETTERS*/
	/**
	 * @return The time at which the Event starts.
	 */
	public String get_dateBegin() {
		return _dateBegin;
	}
	/**
	 * @return The time at which the Event ends.
	 */
	public String get_dateEnd() {
		return _dateEnd;
	}
	/**
	 * @return The summary of the Event
	 */
	public String get_summary() {
		return _summary;
	}
	/**
	 * @return The categories of the Event.
	 */
	public String get_categories() {
		return _categories;
	}
	/**
	 * @return The transparency of the Event.
	 */
	public String get_transparency() {
		return _transparency;
	}
	/**
	 * @return The location of the Event.
	 */
	public String get_location() {
		return _location;
	}
	/**
	 * @return The description of the Event.
	 */
	public String get_description() {
		return _description;
	}
	/*FIN GETTERS*/
	
	/*SETTERS*/

	/**
	 * @param _dateBegin Set the time at which the Event starts.
	 */
	public void set_dateBegin(String _dateBegin) {
		this._dateBegin = _dateBegin;
	}
	/**
	 * @param _dateEnd Set the time at which the Event ends.
	 */
	public void set_dateEnd(String _dateEnd) {
		this._dateEnd = _dateEnd;
	}
	/**
	 * @param _summary Set the summary of the Event.
	 */
	public void set_summary(String _summary) {
		this._summary = _summary;
	}
	/**
	 * @param _categories Set the categories of the Event.
	 */
	public void set_categories(String _categories) {
		this._categories = _categories;
	}
	/**
	 * @param _Transparency Set the transparency of the Event.
	 */
	public void set_transparency(String _Transparency) {
		this._transparency = _Transparency;
	}
	/**
	 * @param _location Set the location of the Event.
	 */
	public void set_location(String _location) {
		this._location = _location;
	}
	/**
	 * @param _description Set the description of the Event.
	 */
	public void set_description(String _description) {
		this._description = _description;
	}
	/*FIN SETTERS*/
	/**
	 * Convert the Vcalendar in a String sequence.
	 */
	/*TOSTRING*/
	public String toString() {
		String 									string = "";
		if(_dateBegin != null)					string+= "Date de Debut	"+get_dateBegin()+"\n";
		if(_dateEnd != null)					string+= "Date de Fin	"+get_dateEnd()+"\n";
		if(_summary != null)					string+= "Résumé		"+get_summary()+"\n";
		if(_categories != null)					string+= "Categories	"+get_categories()+"\n";
		if(_transparency != null)				string+= "Transparence	"+get_transparency()+"\n";
		if(_location != null)					string+= "Localitsation	"+get_location()+"\n";
		if(_description != null)				string+= "Description	"+get_description()+"\n";
		return string;
		
	}

	
	/*FIN TOSTRING*/
}
