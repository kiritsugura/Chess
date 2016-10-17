import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class ActualWindow extends JFrame{
	//for keyboard stuff
	private static final int inFocus=JComponent.WHEN_IN_FOCUSED_WINDOW;
	private static final String PAUSED="game paused";
	//game things
	private double frame,time,mx,my,poxo=205.0,poyo=155.0,ptxo=205.0,ptyo=605.0;
	private int turnCounter=1;
	private String winner;
	private TotalBoard board;
	private Runner game;
	private ChessPieceClass piece;
	//for player 1
	private PawnPiece pawn1,pawn2,pawn3,pawn4,pawn5,pawn6,pawn7,pawn8;
	private KnightPiece knight1,knight2;
	private RookPiece rook1,rook2;
	private BishopPiece bishop1,bishop2;
	private KingPiece king;
	private QueenPiece queen;
	private ChessPieceClass[] player1=new ChessPieceClass[16]; //change when more pieces added.
	//for player 2
	private PawnPiece p1,p2,p3,p4,p5,p6,p7,p8;
	private KnightPiece k1,k2;
	private RookPiece r1,r2;
	private BishopPiece b1,b2;
	private KingPiece k;
	private QueenPiece q;
	private ChessPieceClass[] player2=new ChessPieceClass[16];
	//default states
	private AI ai;
	private Menus state=Menus.MAIN_MENU;
	private states st=states.CANCLICK;
	private ptwo playTwo=ptwo.ISTWO;
	//enums for determining states
	private enum Menus{
		MAIN_MENU,GAME,PAUSED;
	}
	private enum states{
		ISCHOOSING,CANCLICK,HASWON;
	}
	//ai or person player two
	private enum ptwo{
		ISAI,ISTWO;
	}
	public ActualWindow(){
		super.setTitle("Chess Game");
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setSize(800,800);
		super.setResizable(false);
		super.setVisible(true);
		Container pane=super.getContentPane();
		board=new TotalBoard(200.0,200.0,8,8);
		game=new Runner();
		game.setBounds(0,0,800,800);
		pane.setLayout(null);
		pane.add(game);
		game.setVisible(true);
		game.addMouseListener(new mouseInput());
		while(true){
			game.getInputMap(inFocus).put(KeyStroke.getKeyStroke("ESCAPE"),PAUSED);
			game.getActionMap().put(PAUSED,new PausedAction());
			getDelta();
			ai.updateAI(new ArrayList<ChessPieceClass>(Arrays.asList(player2)),new ArrayList<ChessPieceClass>(Arrays.asList(player1)),board);
			game.repaint();
			if(playTwo==ptwo.ISAI && turnCounter%2==0){
				time=getTime();
				ai.decideMoves(turnCounter);
				piece=ai.getPiece();
				if(ai.getMove().hasPiece()){
					ChessPieceClass en=ai.getMove().getPieceOnSquare();
					en.getCurrentTile().pieceRemoved();
					if(en.isPlayerOne()){
						if(en instanceof KingPiece){
							st=states.HASWON;
							winner="Computer Wins!";
						}
						en.setX(poxo);
						en.setY(poyo);
						poxo+=50.0;
						if(poxo>600){
							poxo=205.0;
							poyo+=50;
						}
					}
					en.setIsInPlay(false);
					ai.removeFromTeam(turnCounter,en);
					board.removePieceFromTeams(turnCounter,en);
				}
				piece.getCurrentTile().pieceRemoved();
				piece.setCurrentTile(ai.getMove());
				if(piece instanceof PawnPiece){
					((PawnPiece)piece).setFirstMove(false);
				}
				piece.cancelPiece();
				board.canceled();
				turnCounter++;
				piece=null;
			}
		}
	}
	//for pausing
	private class PausedAction extends AbstractAction{
		public void actionPerformed(ActionEvent e){
			if(state==Menus.PAUSED){
				state=Menus.GAME;
			}
			else if(state==Menus.GAME){
				state=Menus.PAUSED;
			}
			if(st==states.HASWON){
					poxo=205.0;
					poyo=155.0;
					ptxo=205.0;
					ptyo=605.0;
					state=Menus.MAIN_MENU;
					board.resetBoard();
					game.reset();
					turnCounter=1;
					game.repaint();
			}
		}
	}
	private double getTime(){
		return System.currentTimeMillis();
	}
	private void getDelta(){
		frame=getTime();
	}
	private class Runner extends JPanel{
		public Runner(){
			time=getTime();
			setEntities();
			super.setBackground(new Color(200,150,150));
		}
		//render objects
		public void paintComponent(Graphics g){
			super.paintComponent(g);
			Graphics2D g2=(Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			if(st==states.HASWON){
				g2.setFont(new Font("f name",Font.PLAIN,55));
				g2.fillRect(0,0,800,800);
				g2.setColor(Color.blue);
				g2.drawString(winner,200,400);
			}
			else if(state==Menus.GAME){
				g2.setColor(new Color(10,10,10));
				g2.fillRect(200,100,400,100);
				g2.fillRect(200,600,400,100);
				board.draw(g2);
				pawn1.draw(g2);
				pawn2.draw(g2);
				pawn3.draw(g2);
				pawn4.draw(g2);
				pawn5.draw(g2);
				pawn6.draw(g2);
				pawn7.draw(g2);
				pawn8.draw(g2);
				rook1.draw(g2);
				rook2.draw(g2);
				knight1.draw(g2);
				knight2.draw(g2);
				king.draw(g2);
				bishop1.draw(g2);
				bishop2.draw(g2);
				queen.draw(g2);
				//player 2
				p1.draw(g2);
				p2.draw(g2);
				p3.draw(g2);
				p4.draw(g2);
				p5.draw(g2);
				p6.draw(g2);
				p7.draw(g2);
				p8.draw(g2);
				k1.draw(g2);
				k2.draw(g2);
				b1.draw(g2);
				b2.draw(g2);
				r1.draw(g2);
				r2.draw(g2);
				q.draw(g2);
				k.draw(g2);
				if(!(piece==null))
					piece.draw(g2);
			}
			else if(state==Menus.MAIN_MENU){
				g2.setColor(new Color(200,240,250));
				g2.fillRect(0,0,800,800);
				g2.setColor(Color.black);
				g2.drawRect(495,275,200,40);
				g2.drawRect(95,275,200,40);
				g2.setColor(Color.black);
				g2.setFont(new Font("options",Font.PLAIN,25));
				g2.drawString("Two Player Local",500,300);
				g2.drawString("One Player vs AI",100,300);
				g2.setFont(new Font("options",Font.PLAIN,35));
				g2.drawString("Chess Game",300,100);

			}
			else if(state==Menus.PAUSED){
				g2.setColor(new Color(170,200,220));
				g2.fillRect(0,0,800,800);
				g2.setColor(Color.black);
				g2.setFont(new Font("options",Font.PLAIN,25));
				g2.drawString("Unpause",500,300);
				g2.drawString("Main Menu",100,300);
			}
		}

		//set initial values for objects.
		private void setEntities(){
			//change when more pieces added.
			//player 1
			//column 1
			rook1=new RookPiece(205,205,40,40,true);
			ChessPieceClass.addToPieces(rook1);
			bishop1=new BishopPiece(205,255,40,40,true);
			ChessPieceClass.addToPieces(bishop1);
			knight1=new KnightPiece(205,305,40,40,true);
			ChessPieceClass.addToPieces(knight1);
			king=new KingPiece(205,355,40,40,true);
			ChessPieceClass.addToPieces(king);
			queen=new QueenPiece(205,405,40,40,true);
			ChessPieceClass.addToPieces(queen);
			knight2=new KnightPiece(205,455,40,40,true);
			ChessPieceClass.addToPieces(knight2);
			bishop2=new BishopPiece(205,505,40,40,true);
			ChessPieceClass.addToPieces(bishop2);
			rook2=new RookPiece(205,555,40,40,true);
			ChessPieceClass.addToPieces(rook2);
			//column 2
			pawn1=new PawnPiece(255,205,40,40,true);
			ChessPieceClass.addToPieces(pawn1);
			pawn2=new PawnPiece(255,255,40,40,true);
			ChessPieceClass.addToPieces(pawn2);
			pawn3=new PawnPiece(255,305,40,40,true);
			ChessPieceClass.addToPieces(pawn3);
			pawn4=new PawnPiece(255,355,40,40,true);
			ChessPieceClass.addToPieces(pawn4);
			pawn5=new PawnPiece(255,405,40,40,true);
			ChessPieceClass.addToPieces(pawn5);
			pawn6=new PawnPiece(255,455,40,40,true);
			ChessPieceClass.addToPieces(pawn6);
			pawn7=new PawnPiece(255,505,40,40,true);
			ChessPieceClass.addToPieces(pawn7);
			pawn8=new PawnPiece(255,555,40,40,true);
			ChessPieceClass.addToPieces(pawn8);
			//player 2
			//column 1
			r1=new RookPiece(555,205,40,40,false);
			ChessPieceClass.addToPieces(r1);
			b1=new BishopPiece(555,255,40,40,false);
			ChessPieceClass.addToPieces(b1);
			k1=new KnightPiece(555,305,40,40,false);
			ChessPieceClass.addToPieces(k1);
			k=new KingPiece(555,355,40,40,false);
			ChessPieceClass.addToPieces(k);
			q=new QueenPiece(555,405,40,40,false);
			ChessPieceClass.addToPieces(q);
			k2=new KnightPiece(555,455,40,40,false);
			ChessPieceClass.addToPieces(k2);
			b2=new BishopPiece(555,505,40,40,false);
			ChessPieceClass.addToPieces(b2);
			r2=new RookPiece(555,555,40,40,false);
			ChessPieceClass.addToPieces(r2);
			//column 2
			p1=new PawnPiece(505,205,40,40,false);
			ChessPieceClass.addToPieces(p1);
			p2=new PawnPiece(505,255,40,40,false);
			ChessPieceClass.addToPieces(p2);
			p3=new PawnPiece(505,305,40,40,false);
			ChessPieceClass.addToPieces(p3);
			p4=new PawnPiece(505,355,40,40,false);
			ChessPieceClass.addToPieces(p4);
			p5=new PawnPiece(505,405,40,40,false);
			ChessPieceClass.addToPieces(p5);
			p6=new PawnPiece(505,455,40,40,false);
			ChessPieceClass.addToPieces(p6);
			p7=new PawnPiece(505,505,40,40,false);
			ChessPieceClass.addToPieces(p7);
			p8=new PawnPiece(505,555,40,40,false);
			ChessPieceClass.addToPieces(p8);
			//player 1
			//column 1
			player1[0]=rook1;
			player1[1]=rook2;
			player1[2]=bishop1;
			player1[3]=bishop2;
			player1[4]=knight1;
			player1[5]=knight2;
			player1[6]=king;
			player1[7]=queen;
			//column 2
			player1[8]=pawn1;
			player1[9]=pawn2;
			player1[10]=pawn3;
			player1[11]=pawn4;
			player1[12]=pawn5;
			player1[13]=pawn6;
			player1[14]=pawn7;
			player1[15]=pawn8;
			//player 2
			//column 1
			player2[0]=r1;
			player2[1]=r2;
			player2[2]=b1;
			player2[3]=b2;
			player2[4]=k1;
			player2[5]=k2;
			player2[6]=k;
			player2[7]=q;
			//column 2
			player2[8]=p1;
			player2[9]=p2;
			player2[10]=p3;
			player2[11]=p4;
			player2[12]=p5;
			player2[13]=p6;
			player2[14]=p7;
			player2[15]=p8;
			ai=new AI(new ArrayList<ChessPieceClass>(Arrays.asList(player2)),new ArrayList<ChessPieceClass>(Arrays.asList(player1)),board);
			//sets the pieces to their proper place on the board.
			for(int i=0;i<16;i++){
				player1[i].setCurrentTile(board.setPieceToSquare(player1[i]));
				player2[i].setCurrentTile(board.setPieceToSquare(player2[i]));
			}
			board.setTeams(player1,player2);
		}
		//reset Board
		public void reset(){
			ChessPieceClass.resetPieces();
			resetPieces();
			setEntities();
		}
		private void resetPieces(){
			pawn1=null;
			pawn2=null;
			pawn3=null;
			pawn4=null;
			pawn5=null;
			pawn6=null;
			pawn7=null;
			pawn8=null;
			rook1=null;
			rook2=null;
			knight1=null;
			knight2=null;
			king=null;
			bishop1=null;
			bishop2=null;
			queen=null;
			//player 2
			p1=null;
			p2=null;
			p3=null;
			p4=null;
			p5=null;
			p6=null;
			p7=null;
			p8=null;
			k1=null;
			k2=null;
			b1=null;
			b2=null;
			r1=null;
			r2=null;
			q=null;
			k=null;
		}
	}
	//Mouse input for game.
	private class mouseInput implements MouseListener{
		public void mouseClicked(MouseEvent ee){}
		public void mouseEntered(MouseEvent ev){}
		public void mouseExited(MouseEvent eve){}
		public void mouseReleased(MouseEvent eev){}
		public void mousePressed(MouseEvent e){
			if(state==Menus.MAIN_MENU){
				if(e.getButton()==MouseEvent.BUTTON1 && e.getX()>=495 && e.getY()>=275 && e.getX()<=695 && e.getY()<=315){
					state=Menus.GAME;
					playTwo=ptwo.ISTWO;
					game.repaint();
				}
				if(e.getButton()==MouseEvent.BUTTON1 && e.getX()>=95 && e.getY()>=275 && e.getX()<=295 && e.getY()<=315){
					state=Menus.GAME;
					playTwo=ptwo.ISAI;
					game.repaint();
				}
			}
			else if(state==Menus.PAUSED){
				if(e.getButton()==MouseEvent.BUTTON1 && e.getX()>=495 && e.getY()>=275 && e.getX()<=695 && e.getY()<=315){
					state=Menus.GAME;
					game.repaint();
				}
				if(e.getButton()==MouseEvent.BUTTON1 && e.getX()>=95 && e.getY()>=275 && e.getX()<=295 && e.getY()<=315){
					state=Menus.MAIN_MENU;
					board.resetBoard();
					game.reset();
					turnCounter=1;
					game.repaint();
				}

			}
			else if(state==Menus.GAME){
				game.repaint();
				if(frame-time>=500){
					if(st==states.ISCHOOSING){
						if(e.getButton()==MouseEvent.BUTTON1 && piece.findIfClickedBoardPart(e.getX(),e.getY())){
							time=getTime();
							st=states.CANCLICK;
							mx=e.getX();
							my=e.getY();
							ArrayList<BoardParts> bo=piece.getPieceMoves();
							for(int index=0;index<bo.size();index++){
								if(bo.get(index).isInside(mx, my)){
									if(bo.get(index).hasPiece() && !(playTwo==ptwo.ISAI)){
										ChessPieceClass en=bo.get(index).getPieceOnSquare();
										en.getCurrentTile().pieceRemoved();
										if(en.isPlayerOne()){
											if(en instanceof KingPiece){
												st=states.HASWON;
												winner="Player Two Wins!";
											}
											en.setX(poxo);
											en.setY(poyo);
											poxo+=50.0;
											if(poxo>600){
												poxo=205.0;
												poyo-=50;
											}
										}else{
											if(en instanceof KingPiece){
												st=states.HASWON;
												winner="Player One Wins!";
											}
											en.setX(ptxo);
											en.setY(ptyo);
											ptxo+=50.0;
											if(ptxo>600){
												ptxo=205.0;
												ptyo+=50;
											}
										}
										en.setIsInPlay(false);
										board.removePieceFromTeams(turnCounter,en);
										ai.removeFromTeam(turnCounter,en);
									}
									piece.getCurrentTile().pieceRemoved();
									piece.setCurrentTile(bo.get(index));
									if(piece instanceof PawnPiece){
										((PawnPiece)piece).setFirstMove(false);
									}
									break;
								}
							}
							board.canceled();
							piece.cancelPiece();
							turnCounter++;
							game.repaint();
						}
						else if(e.getButton()==MouseEvent.BUTTON3){
							board.canceled();
							piece.cancelPiece();
							piece.isClicked(false);
							piece=null;
							st=states.CANCLICK;
							time=getTime();
						}
					}
					if(st==states.CANCLICK){
						if((e.getButton()==MouseEvent.BUTTON1) && ChessPieceClass.findIfPieceWasClicked(turnCounter,e.getX(),e.getY())){
							piece=ChessPieceClass.findPieceToMove(turnCounter, e.getX(),e.getY());
							mx=e.getX();
							my=e.getY();
							st=states.ISCHOOSING;
							time=getTime();
							piece.isClicked(true);
							piece.Moves(board.findOpenSpots(piece), board.searchForEnemyPieces(turnCounter));
							if(piece.getPieceMoves().size()==0){
								st=states.CANCLICK;
								board.canceled();
								piece.cancelPiece();
								piece.isClicked(false);
								piece=null;
							}
						}
					}
				game.repaint();
				}
			}
		}
	}
}