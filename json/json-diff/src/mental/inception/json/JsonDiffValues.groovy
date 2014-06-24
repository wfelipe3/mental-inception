package mental.inception.json

import groovy.transform.Immutable;

class JsonDiffValues {

	def json1
	def json2

	JsonDiffValues(json1, json2) {
		init(json1, json2)
	}

	boolean areEmpty() {
		return json1.isEmpty() && json2.isEmpty()
	}

	boolean areSameType() {
		return json1.type == json2.type
	}

	private init(json1, json2) {
		this.json1 = new diffValue(value:json1, type:getType(json1))
		this.json2 = new diffValue(value:json2, type:getType(json2))
	}

	private String getType(json) {
		if (json instanceof Map) {
			return "OBJECT"
		} else if (json instanceof List) {
			return "ARRAY"
		}
		throw new RuntimeException("The type of the object $json is not supported")
	}
}
