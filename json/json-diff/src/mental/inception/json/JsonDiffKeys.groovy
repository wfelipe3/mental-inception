package mental.inception.json

import groovy.transform.Immutable;

class JsonDiffKeys {

	def json1
	def json2
	def keysOnlyInJson1
	def keysOnlyInJson2
	def keysInBothJson

	JsonDiffKeys(json1, json2) {
		init(json1, json2)
		initJsonKeys()
	}

	boolean isEmpty() {
		return json1.isEmpty() && json2.isEmpty()
	}

	private init(json1, json2) {
		this.json1 = json1
		this.json2 = json2
	}

	private initJsonKeys() {
		def keysInJson1 = createJson1Keys()
		def keysInJson2 = createJson2Keys()
		createKeysOnlyInJson1(keysInJson1, keysInJson2)
		createKeysOnlyInJson2(keysInJson2, keysInJson1)
		createKeysInBothJson(keysInJson1)
	}

	private def createJson1Keys() {
		return json1.keySet()
	}

	private def createJson2Keys() {
		return json2.keySet()
	}

	private createKeysOnlyInJson1(keysInJson1, keysInJson2) {
		keysOnlyInJson1 = keysInJson1 - keysInJson2
	}

	private createKeysOnlyInJson2(keysInJson2, keysInJson1) {
		keysOnlyInJson2 = keysInJson2 - keysInJson1
	}

	private createKeysInBothJson(keysInJson1) {
		keysInBothJson = keysInJson1 - keysOnlyInJson1
	}
}
