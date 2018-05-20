package elastic;

import model.Master;

import java.util.ArrayList;

/**
 * Created by Michael
 */
public class Utilities {

    public static ArrayList<Master> aMaster;


    static {

        aMaster = new ArrayList<>();

        aMaster.add(new Master("M01", "Inteligencia Artificial", "Este programa se dirige a estudiantes nacionales e internacionales que deseen adquirir conocimientos avanzados en IA para ocupar puestos de trabajo de responsabilidad " +
                "en la industria y los sectores público y académico en Cataluña, España o el extranjero. El programa abarca muchas áreas de investigación relacionadas con el diseño, el análisi y la aplicación de IA." +
                "Este máster universitario capacitará a los estudiantes para: Afrontar problemas técnicamente complejos que requieran conocimientos de innovación y de investigación. " +
                "Tomar decisiones estratégicamente importantes en el entorno profesional. Realizar estudios doctorales en el campo de las tecnologías de la información y la comunicación en la UPC, la URV, la UB o en otras universidades nacionales o internacionales."));
        aMaster.add(new Master("M02", "Ingeniería Informática", "El máster universitario proporciona a los titulados conocimientos y ofrece experiencia en un abanico muy amplio de campos de las tecnologías de la información: desde la informática " +
                "en la nube hasta la seguridad y desde la computación gráfica hasta los sistemas de información, con un enfoque hacia la gestión y el liderazgo en las tecnologías de la información. Los titulados se convertirán en la navaja suiza de las tecnologías de " +
                "la información en las organizaciones donde trabajen"));
        aMaster.add(new Master("M03", "Innovación e Investigación en Informática", "La Informática se ha convertido en la principal fuerza impulsora en muchos campos científicos y tecnológicos. El Máster en Innovación e Investigación en Informática " +
                "ha sido diseñado para proporcionar una sólida formación en diferentes aspectos de la investigación en informática, al tiempo que prepara a sus graduados para convertirse en expertos en cualquiera de los campos de especialización ofrecidos. " +
                "El Máster en Innovación e Investigación en Informática ofrece 6 áreas de especialización:"));
        aMaster.add(new Master("M04", "Project Managament", "El Máster en Project Management, respondiendo a esta demanda empresarial, está diseñado para formar directivos que sepan dirigir, planificar, organizar y controlar proyectos " +
                "complejos y de carácter global de forma eficiente y eficaz. Profesionales capacitados para dirigir equipos de trabajo en base al desarrollo de habilidades directivas y técnicas propias de directivos. En definitiva, Directores de Proyecto que " +
                "sepan cómo alinear las necesidades actuales de las empresas en la dirección de proyectos con las exigencias actuales que plantea el estándar global del Project Management Institute (PMI): minimizar riesgos, crear nuevas oportunidades de " +
                "negocio y alcanzar los objetivos planificados"));
        aMaster.add(new Master("M05", "Marketing", "En la rápida evolución digital, la comunicación online ha abandonado los límites de la web hacia nuevas plataformas y soportes. Las empresas demandan profesionales actualizados capaces " +
                "de responder a estos nuevos retos. Con el Máster en Comunicación y Marketing Digital, obtienes una completa visión para liderar un proyecto digital y dominar todas sus disciplinas como lenguaje y negocio web, e-marketing y tecnología. " +
                "El máster te ofrece una formación integrada, exigente y práctica para descubrir nuevos caminos profesionales y entender oportunidades de negocio online cada vez más dinámicas. Si eres profesional dentro del ámbito de la comunicación y el " +
                "marketing, desarrollador o emprendedor, esta es tu mejor opción para convertirte en un experto en Internet capaz de gestionar y rentabilizar cualquier proyecto.Esta nueva edición consolida la apuesta de éxito de la Universidad " +
                "Autónoma de Barcelona por un máster multidisciplinar y único"));
        aMaster.add(new Master("M06", "Other"));
    }


    /**
     * getNewMasterJson
     * @param index
     * @return
     */
    public static String getNewMasterJson(int index) {
        String s;
        if (aMaster.get(index).getDescripcion() != null){
            s = "{" +
                    "\"masterID\":\""+aMaster.get(index).getMasterID()+"\"," +
                    "\"master\":\""+aMaster.get(index).getMaster()+"\"," +
                    "\"descripcion\":\""+aMaster.get(index).getDescripcion()+"\"" +
                    "}";
        } else {
            s = "{" +
                    "\"masterID\":\""+aMaster.get(index).getMasterID()+"\"," +
                    "\"master\":\""+aMaster.get(index).getMaster() +"\"" +
                    "}";
        }
        return s;
    }

    /**
     * print String with tab
     * @param s
     */
    public static void printS(String s){
        System.out.println("\n"+s);
    }

}
