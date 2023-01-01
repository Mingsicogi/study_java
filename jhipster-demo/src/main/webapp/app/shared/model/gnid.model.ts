import { IGame } from '@/shared/model/game.model';
import { INid } from '@/shared/model/nid.model';
import { ISpid } from '@/shared/model/spid.model';

export interface IGnid {
  id?: number;
  gnid?: number | null;
  gameCd?: string | null;
  game?: IGame | null;
  spidLists?: INid[] | null;
  spid?: ISpid | null;
}

export class Gnid implements IGnid {
  constructor(
    public id?: number,
    public gnid?: number | null,
    public gameCd?: string | null,
    public game?: IGame | null,
    public spidLists?: INid[] | null,
    public spid?: ISpid | null
  ) {}
}
