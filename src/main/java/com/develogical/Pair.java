package com.develogical;

/**
 * Created by kwok on 13/07/2016.
 */
public class Pair<T, U> {
    public final T fst;
    public final U snd;

    public Pair(T fst, U snd) {
        this.fst = fst;
        this.snd = snd;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!fst.equals(pair.fst)) return false;
        return snd.equals(pair.snd);

    }

    @Override
    public int hashCode() {
        int result = fst.hashCode();
        result = 31 * result + snd.hashCode();
        return result;
    }
}
