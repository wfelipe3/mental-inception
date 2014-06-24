package mental.inception.format


class JsonFormatFactory {
	def getFormatter(type) {
		switch(type) {
			case "OBJECT":
				return new ObjectNodeJson()
			case "ARRAY":
				return new ArrayNodeJson()
			case "VALUE":
				return new ValueNodeJson()
			default:
				throw new RuntimeException("The type $type was not found")
		}
	}
}
