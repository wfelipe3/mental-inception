package mental.inception.json

import groovy.transform.Immutable;

class JsonDiffValues {

	def json1
	def json2

	JsonDiffValues(json1, json2) {
		init(json1, json2)
	}

	boolean areEmpty() {
		return isEmpty(json1) && isEmpty(json2)
	}

	def getType() {
		if (!areSameType()) {
			return "DIFF"
		} else {
			return getTypeIn(json1)
		}
	}

	boolean areSameType() {
		return getTypeIn(json1) == getTypeIn(json2)
	}

	private init(json1, json2) {
		this.json1 = createDiffValueFor(json1)
		this.json2 = createDiffValueFor(json2)
	}

	private createDiffValueFor(json1) {
		return new diffValue(value:json1, type:infferTypeFor(json1))
	}

	private boolean isEmpty(json) {
		return json.isEmpty()
	}

	private def getTypeIn(json) {
		return json.type
	}

	private String infferTypeFor(json) {
		if (isObject(json))
			return "OBJECT"
		else if (isArray(json))
			return "ARRAY"
		else
			throwNotSupportedJsonException()
	}

	private isObject(json) {
		return json instanceof Map
	}

	private isArray(json) {
		return json instanceof List
	}

	private void throwNotSupportedJsonException() {
		throw new RuntimeException("The type of the object $json is not supported")
	}
}
