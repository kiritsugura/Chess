
import java.awt.*;
import java.util.*;
import java.awt.image.BufferedImage;

//for inheritance
public abstract class ChessPieceClass {
	protected double x,y,width,height;
	protected boolean playerOne,isInPlay=true,canDraw,clicked;
	protected ArrayList<BoardParts> pieceMoves=new ArrayList<BoardParts>();
	protected BoardParts currentLocation;
	protected BufferedImage pieceImage;
	private static ChessPieceClass[] piecesMade=new ChessPieceClass[32];
	private static int numOfPieces=0;
	//for number of pieces created(32 total).
	public static int getNumPieces(){
		return numOfPieces;
	}
	public static void setNumOfPieces(int num){
		numOfPieces=num;
	}
	public void setLocation(double x, double y){
		this.x=x;
		this.y=y;
	}
	public void setX(double x){
		this.x=x;
	}
	public void setY(double y){
		this.y=y;
	}
	public void setWidth(double width){
		this.width=width;
	}
	public void setHeight(double height){
		this.height=height;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public double getHeight(){
		return height;
	}
	public double getWidth(){
		return width;
	}
	//if the piece has been clicked.
	public boolean isClicked(boolean t){
		clicked=t;
		return t;
	}
	//determines teams
	public boolean isPlayerOne(){
		return playerOne;
	}
	public void setIsPlayerOne(boolean i){
		playerOne=i;
	}
	//draw method every piece needs
	public void draw(Graphics2D g){
		//for the possible moves(if applicable)
		if(canDraw){
			g.setColor(Color.blue);
			for(int i=0;i<pieceMoves.size();i++){
				g.drawRect((int)(pieceMoves.get(i).getX()+5),(int)(pieceMoves.get(i).getY()+5),40,40);
			}
		}
		//draws the piece
		g.drawImage(pieceImage,null,(int)x,(int)y);
	}
	//for checking to see if the mouse selected the square
	public boolean containsPoint(double x,double y){
		if(this.x<=x && this.y<=y && this.x+width>=x && this.y+height>=y)
			return true;
		else
			return false;
	}
	//if the piece has not be taken out of play
	public boolean getIsInPlay(){
		return isInPlay;
	}
	public void setIsInPlay(boolean b){
		isInPlay=b;
	}
	//List of boardparts containing the potential moves
	public ArrayList<BoardParts> getPieceMoves(){
		return pieceMoves;
	}
	//used to cancel a move or move onto next player.
	public void cancelPiece(){
		pieceMoves.removeAll(pieceMoves);
		canDraw=false;
	}
	//returns current board part the piece is on.
	public BoardParts getCurrentTile(){
		return currentLocation;
	}
	//changes the location of the piece
	public void setCurrentTile(BoardParts b){
		b.pieceRemoved();
		currentLocation=b;
		currentLocation.setPieceOnSquare(this);
		this.x=b.getX()+5.0;
		this.y=b.getY()+5.0;
		canDraw=false;
	}
	//movement for other pieces.
	public abstract void Moves(ArrayList<BoardParts> open,ArrayList<BoardParts> enemyPieceSquares);
	//used to determine which chess piece was clicked
	public static ChessPieceClass findPieceToMove(int counter,int mouseX,int mouseY){
		for(int i=0;i<numOfPieces;i++){
			ChessPieceClass piece=piecesMade[i];
			//if the piece is one of Player Ones.
			if(counter%2==1 && piece.playerOne){
				if(piece.x<=mouseX && piece.y<=mouseY && piece.x+piece.width>=mouseX && piece.y+piece.height>=mouseY && piece.isInPlay){
					return piece;
				}
			}else if(counter%2==0 && !piece.playerOne){
				if(piece.x<=mouseX && piece.y<=mouseY && piece.x+piece.width>=mouseX && piece.y+piece.height>=mouseY && piece.isInPlay){
					return piece;
				}
			}
		}
		return null;
	}
	public boolean findIfClickedBoardPart(int mouseX,int mouseY){
		for(int i=0;i<pieceMoves.size();i++){
			if(pieceMoves.get(i).isInside(mouseX,mouseY)){
				return true;
			}
		}
		return false;
	}
	//used to determine if findPieceToMove method will work.
	public static boolean findIfPieceWasClicked(int counter,int mouseX,int mouseY){
			for(int i=0;i<numOfPieces;i++){
				ChessPieceClass piece=piecesMade[i];
				//if the piece is one of Player Ones.
				if(counter%2==1 && piece.playerOne && piece.isInPlay){
					if(piece.x<=mouseX && piece.y<=mouseY && piece.x+piece.width>=mouseX && piece.y+piece.height>=mouseY){
						return true;
					}
				}else if(counter%2==0 && !piece.playerOne && piece.isInPlay){
					if(piece.x<=mouseX && piece.y<=mouseY && piece.x+piece.width>=mouseX && piece.y+piece.height>=mouseY){
						return true;
					}
				}
			}
			return false;
	}
	//for list of total pieces
	public static void addToPieces(ChessPieceClass c){
		piecesMade[numOfPieces]=c;
		numOfPieces++;
	}
	public static void resetPieces(){
		numOfPieces=0;
	}
	public static ChessPieceClass[] getAllPieces(){
		return piecesMade;
	}

}
