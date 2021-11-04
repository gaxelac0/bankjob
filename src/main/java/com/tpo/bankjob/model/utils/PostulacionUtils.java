package com.tpo.bankjob.model.utils;

import java.util.Iterator;
import java.util.List;

import com.tpo.bankjob.model.vo.SkillVO;

public class PostulacionUtils {
	
	public static boolean matchHabilidades(List<SkillVO> required, List<SkillVO> have) {
		
		// TODO quadratic performance, enhance this
		
		// start thinking the postulante has every required skill by the publication
		boolean hasRequired = true;
		Iterator<SkillVO> reqIter = required.iterator();
		while(reqIter.hasNext() && hasRequired) {
			
			SkillVO requiredSkill = reqIter.next();
			
			// start thinking is not found until it is (for mandatory ones)
			boolean found = requiredSkill.isMandatory() ? false : true;
			
			// iter the having skills until one having skill match the required skill
			Iterator<SkillVO> haveIter = have.iterator();
			while(haveIter.hasNext() && !found) {
				
				SkillVO havingSkill = haveIter.next();
				
				// equality means name==name
				found = requiredSkill.getName().trim().equalsIgnoreCase(havingSkill.getName().trim());
			}
			
			hasRequired = found;
		}
	
		return hasRequired;
	}
}
