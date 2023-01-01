import { IGnid } from '@/shared/model/gnid.model';
import { IPid } from '@/shared/model/pid.model';

export interface ISpid {
  id?: number;
  spid?: number | null;
  email?: string | null;
  spidLists?: IGnid[] | null;
  pid?: IPid | null;
}

export class Spid implements ISpid {
  constructor(
    public id?: number,
    public spid?: number | null,
    public email?: string | null,
    public spidLists?: IGnid[] | null,
    public pid?: IPid | null
  ) {}
}
