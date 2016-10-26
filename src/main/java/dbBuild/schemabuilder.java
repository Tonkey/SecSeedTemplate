package dbBuild;

import javax.persistence.Persistence;

/**
 *
 * @author nickl
 */
public class schemabuilder {
    
    public static void main(String[] args) {
        Persistence.generateSchema("lam_seedMaven_war_1.0-SNAPSHOTPU", null);
    }
    
}
