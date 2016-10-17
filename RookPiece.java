import java.awt.*;
import java.util.*;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.*;

//edit moves
public class RookPiece extends ChessPieceClass {

	public RookPiece(double x,double y,double width,double height,boolean isPo){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		playerOne=isPo;
		try{
			if(playerOne){
				pieceImage=ImageIO.read(new File("FilteredTextures//rook.png"));
			}else{
				pieceImage=ImageIO.read(new File("FilteredTextures//rookTwo.png"));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void Moves(ArrayList<BoardParts> open,ArrayList<BoardParts> enemyPieceSquares){
		for(int index=0;index<enemyPieceSquares.size();index++){
			if(((enemyPieceSquares.get(index).getX()/50.0)==(this.x-5)/50.0) || (((enemyPieceSquares.get(index).getY())/50.0)==(this.y-5)/50.0)){
				pieceMoves.add(enemyPieceSquares.get(index));
			}
		}
		for(int index=0;index<open.size();index++){
			if(((open.get(index).getX()/50.0)==(this.x-5)/50.0) || (((open.get(index).getY())/50.0)==(this.y-5)/50.0)){
				pieceMoves.add(open.get(index));
			}
		}
		sortList(pieceMoves,enemyPieceSquares);
		canDraw=true;
	}
	//now works!
	public void sortList(List<BoardParts> li,List<BoardParts> enemy){
		List<BoardParts> west=new ArrayList<BoardParts>();
		List<BoardParts> east=new ArrayList<BoardParts>();
		List<BoardParts> north=new ArrayList<BoardParts>();
		List<BoardParts> south=new ArrayList<BoardParts>();
		for(int i=0;i<li.size();i++){
			if(li.get(i).getX()<=this.x && li.get(i).getY()==this.y-5.0){
				west.add(li.get(i));
			}
			else if(li.get(i).getX()>=this.x && li.get(i).getY()==this.y-5.0){
				east.add(li.get(i));
			}
			else if(li.get(i).getY()<=this.y && li.get(i).getX()==this.x-5.0){
				north.add(li.get(i));
			}
			else if(li.get(i).getY()>=this.y && li.get(i).getX()==this.x-5.0){
				south.add(li.get(i));
			}
		}
		double tx=this.x-5.0;
		double ty=this.y-5.0;
		List<BoardParts> tli=new ArrayList<BoardParts>();
		if(!(west.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(tx-50.0==li.get(i).getX()){
					tx-=50.0;
					tli.add(li.get(i));
					if(enemy.contains(li.get(i))){
						break;
					}
					i=-1;
				}
			}
			tx=this.x-5.0;
			ty=this.y-5.0;
		}
		if(!(east.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(tx+50.0==li.get(i).getX()){
					tx+=50.0;
					tli.add(li.get(i));
					if(enemy.contains(li.get(i))){
						break;
					}
					i=-1;
				}
			}
			tx=this.x-5.0;
			ty=this.y-5.0;
		}
		if(!(north.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(ty-50.0==li.get(i).getY()){
					ty-=50.0;
					tli.add(li.get(i));
					if(enemy.contains(li.get(i))){
						break;
					}
					i=-1;
				}
			}
			tx=this.x-5.0;
			ty=this.y-5.0;
		}
		if(!(south.isEmpty())){
			for(int i=0;i<li.size();i++){
				if(ty+50.0==li.get(i).getY()){
					ty+=50.0;
					tli.add(li.get(i));
					if(enemy.contains(li.get(i))){
						break;
					}
					i=-1;
				}
			}
			tx=this.x-5.0;
			ty=this.y-5.0;
		}
		li.removeAll(li);
		li.addAll(tli);
	}
}
