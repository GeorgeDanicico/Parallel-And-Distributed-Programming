using Lab4.impl;

namespace Lab4;

class Program
{
    static void Main()
    {
        var hosts = new List<string> {
            "www.cs.ubbcluj.ro/~rlupsa/edu/pdp/index.html",
            "www.columbia.edu/~fdc/sample.html"
        };

        // DirectCallback.Run(hosts);
        // TasksMechanism.Run(hosts);
        AsyncTasksMechanism.Run(hosts);
    }
}