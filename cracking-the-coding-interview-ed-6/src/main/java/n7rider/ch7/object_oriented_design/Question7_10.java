package n7rider.ch7.object_oriented_design;

/**
 * Minesweeper: Design and implement a text-based Minesweeper game. Minesweeper is the classic
 * single-player computer game where an NxN grid has B mines (or bombs) hidden across the grid. The
 * remaining cells are either blank or have a number behind them. The numbers reflect the number of
 * bombs in the surrounding eight cells. The user then uncovers a cell. If it is a bomb, the player loses.
 * If it is a number, the number is exposed. If it is a blank cell, this cell and all adjacent blank cells (up to
 * and including the surrounding numeric cells) are exposed. The player wins when all non-bomb cells
 * are exposed. The player can also flag certain places as potential bombs. This doesn't affect game
 * play, other than to block the user from accidentally clicking a cell that is thought to have a bomb.
 * (Tip for the reader: if you're not familiar with this game, please play a few rounds on line first.)
 *
 *
 * After comparing with solution:
 * The book recommends separating Board and Game anyway. It suggests erring on the side of more organization
 * if unsure. The solution has a board inside the game class (not the other way around, like I do). So, the game considers
 * board as one of the items needed for the game. Whereas I considered the board as the server which can initiate a match (i.e., a game).
 * I mistook a match for the game.
 *
 */
public class Question7_10 {

}

/**
 * Board
 * Tile
 *
 * Fields and methods:
 *
 * Board
 *   Tile[][] tiles
 *
 *   startGame
 *   endGame
 *   clickTile(int row, int col)
 *     exposeTiles(int row, int col)
 *   renderBoard
 *
 * Tile
 *   isMine: bool
 *   playerFlag: bool
 *   num: int // default: -1
 *   exposed: bool
 *
 * Flow:
 *   startGame(int N, int b)
 *     for i = ... N
 *      for j = .. N
 *        new Tile()
 *
 *     temp = 0
 *     while temp < b
 *       rand(N * N) / N
 *       if !(tiles[quotient][mod].isMine)
 *         isMine = true
 *         temp++
 *
 *   endGame(bool)
 *     if(true)
 *       Print Win
 *     else
 *       Print Lose
 *     // Can print stats too
 *
 *
 *   clickTile(row, col)
 *     if(tiles[row][col].isMine)
 *       expose all tiles (for i, j loop)
 *       highlight clicked tile // add a new method in Tile for that.
 *       endGame(false)
 *
 *     currTile.exposed = true // safeguards against surr tile calling this tile, and resulting in an endless loop
 *     changeSurrTiles(currTile)
 *       for x = i-1 to i+1
 *         for y = j-1 to j+1
 *           if(x == 0 and y == 0)
 *             skip // no self checks
 *           if tempTile.exposed = true
 *             skip // no need to look at exposed tiles
 *           else
 *             if(tempTile.isMine)
 *               currTile.mine++
 *             else
 *               add tempTile to queue
 *
 *
 *
 *       for each tempTile in queue
 *          changeSurrTiles(currTile)
 *
 *   renderTiles
 *     if exposed
 *       show num value // for 0, most games show a flat blank. Actual blanks have a bevel
 *     if playerFlag
 *       show flag
 *     else
 *       display blank
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

