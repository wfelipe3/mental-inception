package mental.inception.format


class JsonFormatFactory {
	def getFormatter(type) {
		switch(type) {
			case "ROOT":
				return new RootNodeJson()
			case "OBJECT":
				return new ObjectNodeJson()
			case "EMPTY":
				return new EmptyNodeJson()
			case "VALUE":
				return new ValueNodeJson()
			default:
				throw new RuntimeException("The type $type was not found")
		}
	}
}
