using System;

namespace XboxControllerInput
{
#if WINDOWS || XBOX
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        static void Main(string[] args)
        {
            using (XboxControllerInput game = new XboxControllerInput())
            {
                game.Run();
            }
        }
    }
#endif
}

