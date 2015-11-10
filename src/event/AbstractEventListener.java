package event;

public interface AbstractEventListener<M extends AbstractEvent<?>> {

	 public void handleEvent(M event);

}
