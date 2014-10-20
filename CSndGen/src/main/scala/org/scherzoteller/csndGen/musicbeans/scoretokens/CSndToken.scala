package org.scherzoteller.csndGen.musicbeans.scoretokens

import org.apache.commons.lang.StringUtils

trait CSndToken {
  def getValueAsString(): String;
}

object CSndToken {
	def getValueAsString(value: Object) = {
		if(value == null || StringUtils.EMPTY.equals(value)){
			"."
		}else{
			value.toString();
		}
	}
}