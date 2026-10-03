package gen;

import me.supcheg.javafile.facts.meta.Facts;
import p.Comp;
import p.Def;
import p.Disjoint;
import p.Empty;
import p.Fn;
import p.Fn2;
import p.Gen;
import p.Hid;
import p.Hid2;
import p.Nested;
import p.OneWithout;
import p.Redecl;
import p.Run;
import p.Same;
import p.Sealed;
import p.SealedSub;
import p.Sub;
import p.Sub2;
import p.Sub3;
import p.StrFn;
import p.StrOp;
import p.Two;
import p.Wide;
import p.WithDefault;

@Facts({
    Fn.class, Sub.class, Sub2.class, Redecl.class, Run.class, Two.class, Wide.class, Empty.class, Gen.class,
    Def.class, Comp.class, WithDefault.class, StrFn.class, StrOp.class, Hid.class, Hid2.class, Sub3.class,
    Fn2.class, Sealed.class, SealedSub.class, Disjoint.class, Nested.class, Same.class, OneWithout.class
})
class G {}
