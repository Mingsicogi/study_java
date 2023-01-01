import { IGame } from '@/shared/model/game.model';

export interface IGameServer {
  id?: number;
  gameServerId?: string | null;
  region?: string | null;
  game?: IGame | null;
}

export class GameServer implements IGameServer {
  constructor(public id?: number, public gameServerId?: string | null, public region?: string | null, public game?: IGame | null) {}
}
