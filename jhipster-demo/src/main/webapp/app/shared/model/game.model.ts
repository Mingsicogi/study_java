import { IGameServer } from '@/shared/model/game-server.model';

export interface IGame {
  id?: number;
  gameId?: number | null;
  gameNm?: string | null;
  gameCd?: string | null;
  gameServerLists?: IGameServer[] | null;
}

export class Game implements IGame {
  constructor(
    public id?: number,
    public gameId?: number | null,
    public gameNm?: string | null,
    public gameCd?: string | null,
    public gameServerLists?: IGameServer[] | null
  ) {}
}
