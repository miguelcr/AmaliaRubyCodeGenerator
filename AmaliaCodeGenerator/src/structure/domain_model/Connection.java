package structure.domain_model;

public class Connection {
	private String connectionId;
	private Entity source;
	private Entity destination;
	private ConnectionType type;
	private String sourceCardinality;
	private String destinationCardinality;
	
	public Connection(String connectionId, Entity source, Entity destination, ConnectionType type, 
			String sourceCardinality, String destinationCardinality) {		
		this.source = source;
		this.connectionId = connectionId;
		this.destination = destination;
		this.type = type;
		this.sourceCardinality = sourceCardinality;
		this.destinationCardinality = destinationCardinality;
	}

	public String getConnectionId() {
		return connectionId;
	}

	public void setConnectionId(String connectionId) {
		this.connectionId = connectionId;
	}

	public Entity getSource() {
		return source;
	}

	public void setSource(Entity source) {
		this.source = source;
	}

	public Entity getDestination() {
		return destination;
	}

	public void setDestination(Entity destination) {
		this.destination = destination;
	}

	public ConnectionType getType() {
		return type;
	}

	public void setType(ConnectionType type) {
		this.type = type;
	}

	public String getSourceCardinality() {
		return sourceCardinality;
	}

	public void setSourceCardinality(String sourceCardinality) {
		this.sourceCardinality = sourceCardinality;
	}

	public String getDestinationCardinality() {
		return destinationCardinality;
	}

	public void setDestinationCardinality(String destinationCardinality) {
		this.destinationCardinality = destinationCardinality;
	}

}
