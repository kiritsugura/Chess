import java.util.*;
import java.awt.*;
public class TotalBoard {
	private double x,y,bx,by;
	private int width,height,counter=1;
	private BoardParts[][] board;
	private ArrayList<BoardParts> openSpots,enemySpots;
	//pass in array and update regularly.
	private ArrayList<ChessPieceClass> playerOne=new ArrayList<ChessPieceClass>(),playerTwo=new ArrayList<ChessPieceClass>();
	public TotalBoard(double x,double y,int width,int height){
		this.x=x;
		this.y=y;
		this.width=width*50;
		this.height=height*50;
		openSpots=new ArrayList<BoardParts>();
		enemySpots=new ArrayList<BoardParts>();
		board=new BoardParts[width][height];
		by=this.y;
		for(int t=0;t<8;t++){
			bx=this.x;
			for(int p=0;p<8;p++){
				board[t][p]=new BoardParts(bx,by,50,50);
				board[t][p].setBOR(counter);
				counter++;
				bx+=50.0;
			}
			counter++;
			by+=50.0;
		}
	}
	public double getWidth(){
		return width;
	}
	public double getHeight(){
		return height;
	}
	public double getX(){
		return x;
	}
	public double getY(){
		return y;
	}
	public void draw(Graphics2D g){
		for(int i=0;i<8;i++){
			for(int index=0;index<8;index++){
				board[i][index].draw(g);
			}
		}
	}
	//finds the specific square on the board
	public BoardParts findSquare(double x,double y){
		for(int i=0;i<8;i++){
			for(int index=0;index<8;index++){
				if(board[i][index].getX()<x && board[i][index].getY()<y && board[i][index].getX()+50.0>x && board[i][index].getY()+50.0>y){
					return board[i][index];
				}
			}
		}
		return null;
	}
	//changes the piece to  new square once it is removed.
	public BoardParts setPieceToSquare(ChessPieceClass piece){
		for(int i=0;i<8;i++){
			for(int index=0;index<8;index++){
				if(board[i][index].getX()<piece.getX() && board[i][index].getY()<piece.getY() && board[i][index].getX()+50.0>piece.getX() && board[i][index].getY()+50.0>piece.getY()){
					board[i][index].setPieceOnSquare(piece);
					return board[i][index];
				}
			}
		}
		return null;
	}
	//remove a piece from previous square after it is moved elsewhere.
	public void removePieceFromSquare(ChessPieceClass piece){
		for(int i=0;i<8;i++){
			for(int index=0;index<8;index++){
				if(board[i][index].getX()<piece.getX() && board[i][index].getY()<piece.getY() && board[i][index].getX()+50.0>piece.getX() && board[i][index].getY()+50.0>piece.getY()){
					board[i][index].pieceRemoved();
				}
			}
		}
	}
	//called during setup to set up teams.
	public void setTeams(ChessPieceClass[] p1,ChessPieceClass[] p2){
		for(int i=0;i<16;i++){
			playerOne.add(p1[i]);
			playerTwo.add(p2[i]);
		}
	}
	//for searching for spots enemy pieces are in later. pass in counter used for turns
	public ArrayList<BoardParts> searchForEnemyPieces(int counter){
		ArrayList<ChessPieceClass> activeTeam;
		if(counter%2==1)
			activeTeam=playerTwo;
		else
			activeTeam=playerOne;
		for(int i=0;i<activeTeam.size();i++)
			enemySpots.add(activeTeam.get(i).getCurrentTile());
		return enemySpots;
	}
	//adds open spots to a list.
	public ArrayList<BoardParts> findOpenSpots(ChessPieceClass c){
		for(int i=0;i<board.length;i++){
			for(int index=0;index<board[i].length;index++){
				if(!board[i][index].hasPiece()){
					openSpots.add(board[i][index]);
				}
			}
		}
		return openSpots;
	}
	//the clicked piece is canceled
	public void canceled(){
		openSpots.removeAll(openSpots);
		enemySpots.removeAll(enemySpots);
	}
	//remove a piece that was taken out of the game
	public void removePieceFromTeams(int counter,ChessPieceClass c){
		if(counter%2==1)
			playerTwo.remove(c);
		else
			playerOne.remove(c);

	}
	//reset game
	public void resetBoard(){
		for(int i=0;i<8;i++){
			for(int index=0;index<8;index++){
				board[i][index].pieceRemoved();

			}
		}
	}
}
