package mental.inception.format

import groovy.json.JsonBuilder

class ValueNodeJson {

	def format(node) {
		def oldValue = node.value.oldValue
		def newValue = node.value.newValue
		def type = node.value.type
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
}
