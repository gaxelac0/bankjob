package com.tpo.bankjob.model.utils;

import java.util.Iterator;
import java.util.List;

import com.tpo.bankjob.model.Skill;

public class PostulacionUtils {
	
	public static boolean matchHabilidades(List<Skill> required, List<Skill> have) {
		
		// TODO quadratic performance, enhance this
		
		// start thinking the postulante has every required skill by the publication
		boolean hasRequired = true;
		Iterator<Skill> reqIter = required.iterator();
		while(reqIter.hasNext() && hasRequired) {
			
			Skill requiredSkill = reqIter.next();
			
			// start thinking is not found until it is (for mandatory ones)
			boolean found = requiredSkill.isMandatory() ? false : true;
			
			// iter the having skills until one having skill match the required skill
			Iterator<Skill> haveIter = have.iterator();
			while(haveIter.hasNext() && !found) {
				
				Skill havingSkill = haveIter.next();
				
				// equality means name==name
				found = requiredSkill.getName().trim().equalsIgnoreCase(havingSkill.getName().trim());
			}
			
			hasRequired = found;
		}
	
		return hasRequired;
	}
}
