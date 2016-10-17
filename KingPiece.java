import java.util.*;
import java.util.Iterator;
import java.util.List;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.*;
//edit moves
public class KingPiece extends ChessPieceClass {
	public KingPiece(double x,double y,double width,double height,boolean isPo){
		this.x=x;
		this.y=y;
		this.width=width;
		this.height=height;
		playerOne=isPo;
		//import images
		try{
			if(playerOne){
				pieceImage=ImageIO.read(new File("FilteredTextures//king.png"));
			}else{
				pieceImage=ImageIO.read(new File("FilteredTextures//kingTwo.png"));
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	//draw piece and moves(if applicable)
	//get possible board parts the piece can move to
	public void Moves(ArrayList<BoardParts> open,ArrayList<BoardParts> enemyPieceSquares){
		for(int i=0;i<open.size();i++){
			if(enemyPieceSquares.size()-1>=i){
				if((enemyPieceSquares.get(i).getX()==this.x+45.0 || enemyPieceSquares.get(i).getX()==this.x-55.0 || enemyPieceSquares.get(i).getX()==this.x-5.0)&&(enemyPieceSquares.get(i).getY()==this.y+45.0 || enemyPieceSquares.get(i).getY()==this.y-55.0 || enemyPieceSquares.get(i).getY()==this.y-5.0)){
					pieceMoves.add(enemyPieceSquares.get(i));
				}
			}
			if((open.get(i).getX()==this.x+45.0 || open.get(i).getX()==this.x-55.0 || open.get(i).getX()==this.x-5.0)&&(open.get(i).getY()==this.y+45.0 || open.get(i).getY()==this.y-55.0 || open.get(i).getY()==this.y-5.0)){
				pieceMoves.add(open.get(i));
			}
		}
		canDraw=true;
	}
}
