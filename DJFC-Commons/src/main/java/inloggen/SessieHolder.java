package inloggen;

public final class SessieHolder {
    private static ThreadLocal<Sessie> sessie = new ThreadLocal<Sessie>();

    public static Sessie get() {
        return SessieHolder.sessie.get();
    }

    public static void set(Sessie auditContext) {
        SessieHolder.sessie.set(auditContext);
    }

    public static void clear() {
        SessieHolder.sessie.remove();
    }
}
