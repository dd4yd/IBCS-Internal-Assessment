import java.io.*;
import java.util.*;

public class Save {
	public void loadSave(File loadPath){
		try{
			Scanner scan = new Scanner(loadPath);
			
			while(scan.hasNext()){
				for(int i = 0; i < Window.room.block.length; i++){
					for(int x = 0; x < Window.room.block[0].length; x++){
						Window.room.block[i][x].groundID = scan.nextInt();
					}
				}
				for(int i = 0; i < Window.room.block.length; i++){
					for(int x = 0; x < Window.room.block[0].length; x++){
						Window.room.block[i][x].airID = scan.nextInt();
					}
				}
			}
			scan.close();
		}
		catch(Exception e){}
	}
}
