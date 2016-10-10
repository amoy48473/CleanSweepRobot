
public class Door implements Barrier{
	private boolean isOpen;
	public Door(boolean isOpen){
		this.isOpen = isOpen;
	}
	public void Open(){
		this.isOpen=true;
	}
	public void Close(){
		this.isOpen=false;
	}
	public boolean isBlocking() {
		return isOpen;
	}

}
