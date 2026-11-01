package gen;

import me.supcheg.javafile.facts.meta.Facts;
import out.OtherOut;
import out.OtherSealed;
import out.Out;
import out.SealedClass;
import out.SealedInterface;

@Facts({SealedClass.class, SealedInterface.class, Out.class, OtherSealed.class, OtherOut.class})
class G {}
