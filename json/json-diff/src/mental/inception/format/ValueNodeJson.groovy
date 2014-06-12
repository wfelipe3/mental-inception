package mental.inception.format

import groovy.json.JsonBuilder

class ValueNodeJson {

	def format(node) {
		def oldValue = node.value.oldValue
		def newValue = node.value.newValue
		String type = getType(oldValue, newValue)
		return buildJson(oldValue, newValue, type)
	}

	private buildJson(oldValue, newValue, type) {
		JsonBuilder builder = new JsonBuilder()
		return builder {
			"old" oldValue
			"new" newValue
			'$type' type
		}
	}

	private String getType(oldValue, newValue) {
		if (oldValue == newValue) {
			return "SAME"
		} else if (oldValue == null) {
			return "ADDED"
		} else if (newValue == null) {
			return "DELETED"
		} else{
			return "MODIFIED"
		}
	}
}
