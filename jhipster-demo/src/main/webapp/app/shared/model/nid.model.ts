import { IGnid } from '@/shared/model/gnid.model';

export interface INid {
  id?: number;
  nid?: number | null;
  gnid?: IGnid | null;
}

export class Nid implements INid {
  constructor(public id?: number, public nid?: number | null, public gnid?: IGnid | null) {}
}
