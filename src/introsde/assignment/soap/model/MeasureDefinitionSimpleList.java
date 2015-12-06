package introsde.assignment.soap.model;

import java.util.ArrayList;
import java.util.List;

public class MeasureDefinitionSimpleList {
	
	List<String> measureType = new ArrayList<String>();


	public MeasureDefinitionSimpleList(){
		List<MeasureDefinition> md = MeasureDefinition.getAll();

		for(int i=0; i<md.size(); i++){
			MeasureDefinition m = md.get(i);
			measureType.add(m.getMeasureName());
		}
	}
}